package com.future.system.service.impl;

import com.future.common.constant.enums.State;
import com.future.common.constant.enums.UserType;
import com.future.common.core.domain.entity.QResource;
import com.future.common.core.domain.entity.Resource;
import com.future.common.core.domain.entity.User;
import com.future.system.dao.ResourceRepo;
import com.future.system.dao.UserRepo;
import com.future.system.service.ResourceService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ResourceServiceImpl implements ResourceService {

    @javax.annotation.Resource(type = ResourceRepo.class)
    private ResourceRepo resourceRepo;
    
    @javax.annotation.Resource(type = UserRepo.class)
    private UserRepo userRepo;
    
    @javax.annotation.Resource(type = JPAQueryFactory.class)
    private JPAQueryFactory queryFactory;

    public List<Resource> listResources(Long userId) {
        return listResources(new Resource(), userId);
    }

    public List<Resource> listResources(Resource resource, Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        QResource qresource = QResource.resource;
        // 管理员
        if(user != null && UserType.ADMIN.equals(user.getUserType())) {
            
        } else {
                
        }
        return null;
    }
    
    public Set<String> selectPermsByUserId(Long userId) {
        return null;
    }
    
    /**
     * 新增资源
     */
    public void addResource(Resource resource) {
        resource.setState(State.VALID);
        resourceRepo.saveAndFlush(resource);
    }

    /**
     * 更新资源
     */
    public void updateResource(Resource resource) {
        resourceRepo.saveAndFlush(resource);
    }

    /**
     * 删除指定资源
     */
    public void deleteById(Long id) {
        resourceRepo.updateState(id, State.DELETED);
    }
    
    /**
     * 检查资源名称是否唯一
     */
    public boolean checkResourceNameUnique(Resource resource) {
        Resource existsResource =
            this.resourceRepo.findByParentIdAndNameAndStateNot(resource.getParentId(), resource.getName(), State.DELETED);
        return existsResource == null || existsResource.getId().equals(resource.getId());
    }

}
