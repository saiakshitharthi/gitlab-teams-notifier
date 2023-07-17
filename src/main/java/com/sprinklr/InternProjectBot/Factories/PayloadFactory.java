package com.sprinklr.InternProjectBot.Factories;


import com.sprinklr.InternProjectBot.Payload.CommentPackage.CommentOnMergeRequestPayloadParser;
import com.sprinklr.InternProjectBot.Database.DataAccessLayer;
import com.sprinklr.InternProjectBot.GitLabAPI;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import com.sprinklr.InternProjectBot.Payload.JobPackage.JobFailedPayloadParser;
import com.sprinklr.InternProjectBot.Payload.JobPackage.JobSucceededPayloadParser;
import com.sprinklr.InternProjectBot.Payload.MergeRequestPackage.*;
import com.sprinklr.InternProjectBot.Payload.PayloadParser;
import com.sprinklr.InternProjectBot.Payload.CommentPackage.CommentOnCommitPayloadParser;
import com.sprinklr.InternProjectBot.Payload.CommentPackage.CommentOnIssuePayloadParser;
import com.sprinklr.InternProjectBot.Payload.CommentPackage.CommentOnSnippetPayloadParser;
import com.sprinklr.InternProjectBot.Payload.PipelinePackage.PipelineFailedPayloadParser;
import com.sprinklr.InternProjectBot.Payload.PipelinePackage.PipelineSucceededPayloadParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class PayloadFactory {
    @Autowired
    private GitLabAPI gitLabAPI;

    @Autowired
    @Qualifier("dataAccessLayerImplMongoDBAtlas")
    private DataAccessLayer dataAccessLayer;
    public PayloadParser getPayloadParser(Payload payload){
        if(payload == null){
            return null;
        }

        if(payload.getObject_kind() == null){
            return null;
        }


        if(payload.getObject_kind().equals("note")){
            if(payload.getObject_attributes().getNoteable_type() == null){
                return null;
            }
            if(payload.getObject_attributes().getNoteable_type().equals("Commit")){
                return new CommentOnCommitPayloadParser(gitLabAPI,dataAccessLayer);
            }
            if(payload.getObject_attributes().getNoteable_type().equals("MergeRequest")){
                return new CommentOnMergeRequestPayloadParser(gitLabAPI,dataAccessLayer);
            }
            if(payload.getObject_attributes().getNoteable_type().equals("Issue")){
                return new CommentOnIssuePayloadParser(gitLabAPI,dataAccessLayer);
            }
            if(payload.getObject_attributes().getNoteable_type().equals("Snippet")){
                return new CommentOnSnippetPayloadParser(gitLabAPI,dataAccessLayer);
            }
        }
        if(payload.getObject_kind().equals("merge_request")){

            if(payload.getObject_attributes().getAction() == null){
                return null;
            }
            if(payload.getObject_attributes().getAction().equals("open")){
                return new MergeRequestOpenedPayloadParser(gitLabAPI);
            }
            if(payload.getObject_attributes().getAction().equals("close")){
                return new MergeRequestClosedPayloadParser(gitLabAPI);
            }
            if(payload.getObject_attributes().getAction().equals("reopen")){
                return new MergeRequestReopenedPayloadParser(gitLabAPI);
            }
            if(payload.getObject_attributes().getAction().equals("merge")){
                return new MergeRequestMergedPayloadParser(gitLabAPI);
            }
            if(payload.getObject_attributes().getAction().equals("update")){
                return new MergeRequestUpdatedPayloadParser(gitLabAPI);
            }
            if(payload.getObject_attributes().getAction().equals("approved")){
                return new MergeRequestApprovedPayloadParser(gitLabAPI);
            }
            if(payload.getObject_attributes().getAction().equals("unapproved")){
                return new MergeRequestUnapprovedPayloadParser(gitLabAPI);
            }
        }
        if(payload.getObject_kind().equals("build")){
            if(payload.getBuild_status() == null){
                return null;
            }


            if(payload.getBuild_status().equals("success")){
                return new JobSucceededPayloadParser(gitLabAPI);
            }
            if(payload.getBuild_status().equals("failed")){
                return new JobFailedPayloadParser(gitLabAPI);
            }
        }
        if(payload.getObject_kind().equals("pipeline")){
            if(payload.getObject_attributes() == null){
                return null;
            }
            if(payload.getObject_attributes().getStatus() == null){
                return null;
            }
            if(payload.getObject_attributes().getStatus().equals("success")){
                return new PipelineSucceededPayloadParser(gitLabAPI);
            }
            if(payload.getObject_attributes().getStatus().equals("failed")){
                return new PipelineFailedPayloadParser(gitLabAPI);
            }
        }


        return null;
    }

}
