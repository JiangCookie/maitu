package com.ljc.maitu.pojo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "order_item")
public class OrderItem {
    /**
     * �����ӱ�id
     */
    @Id
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "order_no")
    private Long orderNo;

    /**
     * ��Ʒid
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * ��Ʒ����
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * ��ƷͼƬ��ַ
     */
    @Column(name = "product_image")
    private String productImage;

    /**
     * ���ɶ���ʱ����Ʒ���ۣ���λ��Ԫ,������λС��
     */
    @Column(name = "current_unit_price")
    private BigDecimal currentUnitPrice;

    /**
     * ��Ʒ����
     */
    private Integer quantity;

    /**
     * ��Ʒ�ܼ�,��λ��Ԫ,������λС��
     */
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * ��ȡ�����ӱ�id
     *
     * @return id - �����ӱ�id
     */
    public Integer getId() {
        return id;
    }

    /**
     * ���ö����ӱ�id
     *
     * @param id �����ӱ�id
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
     * @return order_no
     */
    public Long getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo
     */
    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
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
     * ��ȡ��Ʒ����
     *
     * @return product_name - ��Ʒ����
     */
    public String getProductName() {
        return productName;
    }

    /**
     * ������Ʒ����
     *
     * @param productName ��Ʒ����
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * ��ȡ��ƷͼƬ��ַ
     *
     * @return product_image - ��ƷͼƬ��ַ
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     * ������ƷͼƬ��ַ
     *
     * @param productImage ��ƷͼƬ��ַ
     */
    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    /**
     * ��ȡ���ɶ���ʱ����Ʒ���ۣ���λ��Ԫ,������λС��
     *
     * @return current_unit_price - ���ɶ���ʱ����Ʒ���ۣ���λ��Ԫ,������λС��
     */
    public BigDecimal getCurrentUnitPrice() {
        return currentUnitPrice;
    }

    /**
     * �������ɶ���ʱ����Ʒ���ۣ���λ��Ԫ,������λС��
     *
     * @param currentUnitPrice ���ɶ���ʱ����Ʒ���ۣ���λ��Ԫ,������λС��
     */
    public void setCurrentUnitPrice(BigDecimal currentUnitPrice) {
        this.currentUnitPrice = currentUnitPrice;
    }

    /**
     * ��ȡ��Ʒ����
     *
     * @return quantity - ��Ʒ����
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * ������Ʒ����
     *
     * @param quantity ��Ʒ����
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * ��ȡ��Ʒ�ܼ�,��λ��Ԫ,������λС��
     *
     * @return total_price - ��Ʒ�ܼ�,��λ��Ԫ,������λС��
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * ������Ʒ�ܼ�,��λ��Ԫ,������λС��
     *
     * @param totalPrice ��Ʒ�ܼ�,��λ��Ԫ,������λС��
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}