package com.tang.web.services.impl;

import com.tang.web.dao.ITangVersionDao;
import com.tang.web.entities.version.VersionBO;
import com.tang.web.entities.version.VersionPO;
import com.tang.web.services.ITangVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuma on 2019/12/2.
 */
@Service
public class TangVersionService implements ITangVersionService {

    @Autowired
    private ITangVersionDao versionDao;
    /**
     * 保存应用
     *
     * @param bo
     * @return
     */
    @Override
    public int save(VersionBO bo) {
        VersionPO po  = bo.toPO();

        versionDao.save(po);
        return po.getVersionId();
    }

    /**
     * 查询应用列表
     *
     * @return
     * @param id
     */
    @Override
    public List<VersionPO> list(Integer id) {

        return versionDao.list(id);
    }
}
