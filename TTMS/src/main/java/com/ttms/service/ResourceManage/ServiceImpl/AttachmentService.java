package com.ttms.service.ResourceManage.ServiceImpl;

import com.ttms.Entity.ResoAttachment;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ResoAttachMentMapper;
import com.ttms.service.ProductManage.IProductListService;
import com.ttms.service.ProductManage.IProjectService;
import com.ttms.service.ResourceManage.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class AttachmentService implements IAttachmentService {
    @Autowired
    private ResoAttachMentMapper resoAttachMentMapper;

    @Autowired
    private IProductListService productListService;

    public List<ResoAttachment> getAttachmentsByPid(int pid){
        ResoAttachment resoAttachment = new ResoAttachment();
        resoAttachment.setProductId(pid);
        List<ResoAttachment> resoAttachments = resoAttachMentMapper.select(resoAttachment);
        if(CollectionUtils.isEmpty(resoAttachments)){
            throw new TTMSException(ExceptionEnum.PRODUCT_ATTACHMENT_NOT_FOUND);
        }
        return resoAttachments;
    }

    public Void addAttachment(int pid, String fileName, String fileUrl, String attachementName , int userId) {
        ResoAttachment attachment = new ResoAttachment();
        attachment.setProductId(pid);
        attachment.setAttachmenttitle(attachementName);
        attachment.setFileurl(fileUrl);
        attachment.setFilename(fileName);
        attachment.setInvalid((byte) 1);
        attachment.setUploadtime(new Date());
        attachment.setUploaduserid(userId);
        int i = resoAttachMentMapper.insert(attachment);
        if(i != 1){
            throw new TTMSException(ExceptionEnum.ATTACHMENT_INSERT_FAIL);
        }
        return null;
    }
}
