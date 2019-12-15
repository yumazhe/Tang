package com.tang.web.dao.impl;

import com.tang.web.dao.ITangVersionDao;
import com.tang.web.entities.version.VersionPO;
import com.tang.web.mapper.ITangVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */

@Repository
public class TangVersionDaoImpl implements ITangVersionDao {

    @Autowired
    private ITangVersionMapper versionMapper;

    /**
     * 保存应用版本
     * @param po
     */
    @Override
    public void save(VersionPO po){
        versionMapper.save(po);
    }

    /**
     * 查询应用版本列表
     *
     * @return
     * @param id
     */
    @Override
    public List<VersionPO> list(Integer id) {
        return versionMapper.list(id);
    }

}
