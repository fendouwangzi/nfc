package zzg.com.nfc;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import zzg.com.nfc.net.response.OrderDetailsResponse;

/**
 * Created by Administrator on 2017/9/21.
 */

public class HomeAdapter extends BaseQuickAdapter<OrderDetailsResponse, BaseViewHolder> {

    public HomeAdapter(@LayoutRes int layoutResId, @Nullable List<OrderDetailsResponse> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailsResponse item) {
        helper.setText(R.id.text_ordernum ,"订单号:"+item.getOrderCode());
        helper.setText(R.id.text_money ,"订单金额:"+item.getOrderFee()+"");
        helper.setText(R.id.text_name ,"客户姓名:"+item.getUserName());
        helper.setText(R.id.text_tel,"电话号码:"+item.getUserTel());
//        helper.setText(R.id.text_ordernum ,item.getOrderCode());
        helper.setText(R.id.text_remark,"客户备注:"+item.getRemark());
    }
}
