package com.ptt.pttmanager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptt.pttmanager.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 通用的service类
 * */
@Transactional
public abstract class BaseService<T>{

    @Autowired
    private Mapper<T> mapper;

    /**
     * 根据Id查找
     * */
    public T queryById(Long id){
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有数据
     *
     * */
    public List<T> queryAll(){
        return this.mapper.select(null);
    }

    /**
     * 查询一条数据
     * */
    public List<T> queryOne(T record){
        return this.mapper.select(record);
    }

    /**
     * 跟据条件查询数据列表
     * */
    public List<T> queryListByWhere(T record){
        return this.mapper.select(record);
    }

    /**
     * 分页查询
     * */
    public PageInfo<T> queryPageListByWhere(Integer page,Integer rows,T record){
        PageHelper.startPage(page,rows);
        List<T> list = this.queryListByWhere(record);
        return new PageInfo<T>(list);
    }

    /**
     * 新增数据
     * */
    public Integer save(T record){
        return this.mapper.insert(record);
    }

    /**
     * 新怎数据使用不能为null的字段
     * */

    public Integer saveSelective(T record){
        return this.mapper.insertSelective(record);
    }

    /**
     * 更新数据
     * */
    public Integer update(T record){
        return this.mapper.updateByPrimaryKey(record);
    }

    /**
     * 使用不为null的字段更新数据
     * */
    public Integer updateSelective(T record){
        return this.mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据id删除数据
     * */
    public Integer deleteById(Long id){
        return this.mapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * */
    public Integer deleteByIds(Class<T> clazz,String property,List<Object> ids){
        Example example = new Example(clazz);
        example.createCriteria().andIn(property,ids);
        return this.mapper.deleteByExample(example);
    }

    /**
     * 根据条件删除
     * */
    public Integer deleteByWhere(T record){
        return this.mapper.delete(record);
    }


}
