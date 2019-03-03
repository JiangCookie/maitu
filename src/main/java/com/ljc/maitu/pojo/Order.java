package com.ljc.maitu.pojo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "order")
public class Order {
    /**
     * ����id
     */
    @Id
    private Integer id;

    /**
     * ������
     */
    @Column(name = "order_no")
    private Long orderNo;

    /**
     * �û�id
     */
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "shipping_id")
    private Integer shippingId;

    /**
     * ʵ�ʸ�����,��λ��Ԫ,������λС��
     */
    private BigDecimal payment;

    /**
     * ֧������,1-����֧��
     */
    @Column(name = "payment_type")
    private Integer paymentType;

    /**
     * �˷�,��λ��Ԫ
     */
    private Integer postage;

    /**
     * ����״̬:0-��ȡ��-10-δ���20-�Ѹ��40-�ѷ�����50-���׳ɹ���60-���׹ر�
     */
    private Integer status;

    /**
     * ֧��ʱ��
     */
    @Column(name = "payment_time")
    private Date paymentTime;

    /**
     * ����ʱ��
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * �������ʱ��
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * ���׹ر�ʱ��
     */
    @Column(name = "close_time")
    private Date closeTime;

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
     * ��ȡ����id
     *
     * @return id - ����id
     */
    public Integer getId() {
        return id;
    }

    /**
     * ���ö���id
     *
     * @param id ����id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * ��ȡ������
     *
     * @return order_no - ������
     */
    public Long getOrderNo() {
        return orderNo;
    }

    /**
     * ���ö�����
     *
     * @param orderNo ������
     */
    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * ��ȡ�û�id
     *
     * @return user_id - �û�id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * �����û�id
     *
     * @param userId �û�id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return shipping_id
     */
    public Integer getShippingId() {
        return shippingId;
    }

    /**
     * @param shippingId
     */
    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    /**
     * ��ȡʵ�ʸ�����,��λ��Ԫ,������λС��
     *
     * @return payment - ʵ�ʸ�����,��λ��Ԫ,������λС��
     */
    public BigDecimal getPayment() {
        return payment;
    }

    /**
     * ����ʵ�ʸ�����,��λ��Ԫ,������λС��
     *
     * @param payment ʵ�ʸ�����,��λ��Ԫ,������λС��
     */
    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    /**
     * ��ȡ֧������,1-����֧��
     *
     * @return payment_type - ֧������,1-����֧��
     */
    public Integer getPaymentType() {
        return paymentType;
    }

    /**
     * ����֧������,1-����֧��
     *
     * @param paymentType ֧������,1-����֧��
     */
    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * ��ȡ�˷�,��λ��Ԫ
     *
     * @return postage - �˷�,��λ��Ԫ
     */
    public Integer getPostage() {
        return postage;
    }

    /**
     * �����˷�,��λ��Ԫ
     *
     * @param postage �˷�,��λ��Ԫ
     */
    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    /**
     * ��ȡ����״̬:0-��ȡ��-10-δ���20-�Ѹ��40-�ѷ�����50-���׳ɹ���60-���׹ر�
     *
     * @return status - ����״̬:0-��ȡ��-10-δ���20-�Ѹ��40-�ѷ�����50-���׳ɹ���60-���׹ر�
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * ���ö���״̬:0-��ȡ��-10-δ���20-�Ѹ��40-�ѷ�����50-���׳ɹ���60-���׹ر�
     *
     * @param status ����״̬:0-��ȡ��-10-δ���20-�Ѹ��40-�ѷ�����50-���׳ɹ���60-���׹ر�
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * ��ȡ֧��ʱ��
     *
     * @return payment_time - ֧��ʱ��
     */
    public Date getPaymentTime() {
        return paymentTime;
    }

    /**
     * ����֧��ʱ��
     *
     * @param paymentTime ֧��ʱ��
     */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * ��ȡ����ʱ��
     *
     * @return send_time - ����ʱ��
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * ���÷���ʱ��
     *
     * @param sendTime ����ʱ��
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * ��ȡ�������ʱ��
     *
     * @return end_time - �������ʱ��
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * ���ý������ʱ��
     *
     * @param endTime �������ʱ��
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * ��ȡ���׹ر�ʱ��
     *
     * @return close_time - ���׹ر�ʱ��
     */
    public Date getCloseTime() {
        return closeTime;
    }

    /**
     * ���ý��׹ر�ʱ��
     *
     * @param closeTime ���׹ر�ʱ��
     */
    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
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