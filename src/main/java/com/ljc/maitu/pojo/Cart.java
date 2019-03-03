package com.ljc.maitu.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "cart")
public class Cart {
    @Id
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * ��Ʒid
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * ����
     */
    private Integer quantity;

    /**
     * �Ƿ�ѡ��,1=�ѹ�ѡ,0=δ��ѡ
     */
    private Integer checked;

    /**
     * ����ʱ��
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * ����ʱ��
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * ��ȡ��Ʒid
     *
     * @return product_id - ��Ʒid
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * ������Ʒid
     *
     * @param productId ��Ʒid
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * ��ȡ����
     *
     * @return quantity - ����
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * ��������
     *
     * @param quantity ����
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * ��ȡ�Ƿ�ѡ��,1=�ѹ�ѡ,0=δ��ѡ
     *
     * @return checked - �Ƿ�ѡ��,1=�ѹ�ѡ,0=δ��ѡ
     */
    public Integer getChecked() {
        return checked;
    }

    /**
     * �����Ƿ�ѡ��,1=�ѹ�ѡ,0=δ��ѡ
     *
     * @param checked �Ƿ�ѡ��,1=�ѹ�ѡ,0=δ��ѡ
     */
    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    /**
     * ��ȡ����ʱ��
     *
     * @return create_time - ����ʱ��
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * ���ô���ʱ��
     *
     * @param createTime ����ʱ��
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * ��ȡ����ʱ��
     *
     * @return update_time - ����ʱ��
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * ���ø���ʱ��
     *
     * @param updateTime ����ʱ��
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}