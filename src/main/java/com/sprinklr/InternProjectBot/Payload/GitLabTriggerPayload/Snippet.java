package com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload;

public class Snippet {

    private int id;
    private String title;
    private String content;
    private int author_id;
    private int project_id;
    private String created_at;
    private String updated_at;
    private String file_name;
    private String type;
    private int visibility_level;
    private String description;
    private Object encrypted_secret_token;
    private Object encrypted_secret_token_iv;
    private boolean secret;
    private boolean repository_read_only;
    private Object secret_token;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVisibility_level() {
        return visibility_level;
    }

    public void setVisibility_level(int visibility_level) {
        this.visibility_level = visibility_level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getEncrypted_secret_token() {
        return encrypted_secret_token;
    }

    public void setEncrypted_secret_token(Object encrypted_secret_token) {
        this.encrypted_secret_token = encrypted_secret_token;
    }

    public Object getEncrypted_secret_token_iv() {
        return encrypted_secret_token_iv;
    }

    public void setEncrypted_secret_token_iv(Object encrypted_secret_token_iv) {
        this.encrypted_secret_token_iv = encrypted_secret_token_iv;
    }

    public boolean isSecret() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }

    public boolean isRepository_read_only() {
        return repository_read_only;
    }

    public void setRepository_read_only(boolean repository_read_only) {
        this.repository_read_only = repository_read_only;
    }

    public Object getSecret_token() {
        return secret_token;
    }

    public void setSecret_token(Object secret_token) {
        this.secret_token = secret_token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
