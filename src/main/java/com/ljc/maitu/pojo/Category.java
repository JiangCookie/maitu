package com.ljc.maitu.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "category")
public class Category {
    /**
     * ���Id
     */
    @Id
    private Integer id;

    /**
     * �����id��id=0ʱ˵���Ǹ��ڵ�,һ�����
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * �������
     */
    private String name;

    /**
     * ����ͼƬ��ַ
     */
    @Column(name = "category_image")
    private String categoryImage;

    /**
     * ���״̬1-����,2-�ѷ���
     */
    private Boolean status;

    /**
     * ������,ͬ��չʾ˳��,��ֵ�������Ȼ����
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

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
     * ��ȡ���Id
     *
     * @return id - ���Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * �������Id
     *
     * @param id ���Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * ��ȡ�����id��id=0ʱ˵���Ǹ��ڵ�,һ�����
     *
     * @return parent_id - �����id��id=0ʱ˵���Ǹ��ڵ�,һ�����
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * ���ø����id��id=0ʱ˵���Ǹ��ڵ�,һ�����
     *
     * @param parentId �����id��id=0ʱ˵���Ǹ��ڵ�,һ�����
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * ��ȡ�������
     *
     * @return name - �������
     */
    public String getName() {
        return name;
    }

    /**
     * �����������
     *
     * @param name �������
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * ��ȡ����ͼƬ��ַ
     *
     * @return category_image - ����ͼƬ��ַ
     */
    public String getCategoryImage() {
        return categoryImage;
    }

    /**
     * ���÷���ͼƬ��ַ
     *
     * @param categoryImage ����ͼƬ��ַ
     */
    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage == null ? null : categoryImage.trim();
    }

    /**
     * ��ȡ���״̬1-����,2-�ѷ���
     *
     * @return status - ���״̬1-����,2-�ѷ���
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * �������״̬1-����,2-�ѷ���
     *
     * @param status ���״̬1-����,2-�ѷ���
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * ��ȡ������,ͬ��չʾ˳��,��ֵ�������Ȼ����
     *
     * @return sort_order - ������,ͬ��չʾ˳��,��ֵ�������Ȼ����
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * ����������,ͬ��չʾ˳��,��ֵ�������Ȼ����
     *
     * @param sortOrder ������,ͬ��չʾ˳��,��ֵ�������Ȼ����
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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