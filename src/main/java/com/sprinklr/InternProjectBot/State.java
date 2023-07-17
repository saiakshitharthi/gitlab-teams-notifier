package com.sprinklr.InternProjectBot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "gitLabAuthenticationToken"})
public class State {


    private String gitLabAuthenticationTokenEncrypted;
    private String discussionThreadURL;
    private String gitLabAuthenticationToken;
    private boolean isLoggedin;

    public String getGitLabAuthenticationTokenEncrypted() {
        return gitLabAuthenticationTokenEncrypted;
    }

    public void setGitLabAuthenticationTokenEncrypted(String gitLabAuthenticationTokenEncrypted) {
        try {
            this.gitLabAuthenticationToken = EncryptionUtil.decrypt(gitLabAuthenticationTokenEncrypted,EncryptionUtil.getSecretKey());
        }catch (Exception e){
            e.printStackTrace();
        }
        this.gitLabAuthenticationTokenEncrypted = gitLabAuthenticationTokenEncrypted;
        System.out.println("Decrypted token: "+this.gitLabAuthenticationToken);
    }

    public String getGitLabAuthenticationToken() {
        if(gitLabAuthenticationToken == null){
            try {
                this.gitLabAuthenticationToken = EncryptionUtil.decrypt(gitLabAuthenticationTokenEncrypted,EncryptionUtil.getSecretKey());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return gitLabAuthenticationToken;
    }

    public void setGitLabAuthenticationToken(String gitLabAuthenticationToken) {
        this.gitLabAuthenticationToken = gitLabAuthenticationToken;
        try{
            this.gitLabAuthenticationTokenEncrypted = EncryptionUtil.encrypt(gitLabAuthenticationToken,EncryptionUtil.getSecretKey());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getDiscussionThreadURL() {
        return discussionThreadURL;
    }

    public void setDiscussionThreadURL(String discussionThreadURL) {
        this.discussionThreadURL = discussionThreadURL;
    }

    public boolean getIsLoggedin() {
        return isLoggedin;
    }

    public void setIsLoggedin(boolean loggedin) {
        isLoggedin = loggedin;
    }

    public State() {

    }
}
