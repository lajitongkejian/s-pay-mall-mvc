package cn.nju.edu.dao;

import cn.nju.edu.domain.po.PayOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：IOrderDao
 * 作者：tkj
 * 日期：2024/10/26
 */
@Mapper
public interface IOrderDao {
    void insert(PayOrder payOrder);

    PayOrder queryUnPayOrder(PayOrder payOrder);
}
