package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.ContactType;
import ru.javaops.basejava.webapp.model.Link;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class SqlStorage implements Storage {
    private static final String SELECT_WITH_CONTACTS = "" +
            "   SELECT * " +
            "     FROM resume AS r " +
            "LEFT JOIN contact AS c " +
            "       ON r.uuid = c.resume_uuid";

    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume resume) {
        helper.transactionalExecute(
                conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    try (PreparedStatement ps = conn.prepareStatement(
                            "" +
                                    "INSERT INTO contact (text_link, url_link, type, resume_uuid) " +
                                    "VALUES (?, ?, ?, ?)")) {
                        processContacts(ps, resume);
                    }
                    return null;
                });
    }

    @Override
    public void update(Resume resume) {
        helper.transactionalExecute(
                conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                        ps.setString(1, resume.getFullName());
                        ps.setString(2, resume.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(resume.getUuid());
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement(
                            "" +
                                    "UPDATE contact " +
                                    "   SET text_link = ?, " +
                                    "       url_link = ? " +
                                    " WHERE type = ? " +
                                    "   AND resume_uuid = ?")) {
                        processContacts(ps, resume);
                    }
                    return null;
                });
    }

    @Override
    public void delete(String uuid) {
        helper.<Void>execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.execute(SELECT_WITH_CONTACTS + " WHERE uuid = ?",
                              ps -> {
                                  ps.setString(1, uuid);
                                  ResultSet rs = ps.executeQuery();
                                  if (!rs.next()) {
                                      throw new NotExistStorageException(uuid);
                                  }
                                  Resume resume = new Resume(uuid, rs.getString("full_name"));
                                  do {
                                      setContact(rs, resume);
                                  } while (rs.next());
                                  return resume;
                              });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.execute(SELECT_WITH_CONTACTS + " ORDER BY full_name, uuid",
                              ps -> {
                                  List<Resume> result = new ArrayList<>();
                                  ResultSet rs = ps.executeQuery();
                                  while (rs.next()) {
                                      String uuid = rs.getString("uuid");
                                      Resume resume = new Resume(uuid, rs.getString("full_name"));
                                      do {
                                          setContact(rs, resume);
                                      } while (uuid.equals(rs.getString("uuid")) && rs.next());
                                      result.add(resume);
                                      rs.previous();
                                  }
                                  return result;
                              });
    }

    @Override
    public void clear() {
        helper.<Void>execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public int size() {
        return helper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private static void processContacts(PreparedStatement ps, Resume resume) throws SQLException {
        for (Map.Entry<ContactType, Link> e : resume.getContacts().entrySet()) {
            ps.setString(1, e.getValue().getText());
            ps.setString(2, e.getValue().getUrl());
            ps.setString(3, e.getKey().name());
            ps.setString(4, resume.getUuid());
            ps.addBatch();
        }
        ps.executeBatch();
    }

    private static void setContact(ResultSet rs, Resume resume) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            resume.setContact(ContactType.valueOf(type), new Link(rs.getString("text_link"), rs.getString("url_link")));
        }
    }
}
