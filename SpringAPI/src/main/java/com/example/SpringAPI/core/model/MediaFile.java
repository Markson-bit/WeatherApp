package com.example.SpringAPI.core.model;

import jakarta.persistence.*;

@Entity
@Table(name = "media_file")
public class MediaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] file;

    private String extension;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public MediaFile() {}

    // get/set
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public byte[] getFile() { return file; }
    public void setFile(byte[] file) { this.file = file; }

    public String getExtension() { return extension; }
    public void setExtension(String extension) { this.extension = extension; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
