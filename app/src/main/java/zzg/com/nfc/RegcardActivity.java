package zzg.com.nfc;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zzg.com.nfc.net.api.LoginService;
import zzg.com.nfc.net.base.BaseSubscriber;
import zzg.com.nfc.net.exception.APIException;
import zzg.com.nfc.net.request.MessageSendRequest;
import zzg.com.nfc.net.request.PayRequest;
import zzg.com.nfc.net.request.RegcardRequest;
import zzg.com.nfc.net.response.AllMessageResponse;
import zzg.com.nfc.net.response.OrderDetailsResponse;
import zzg.com.nfc.net.response.PayResponse;
import zzg.com.nfc.net.response.RegcardResponse;
import zzg.com.nfc.ui.base.BaseActivity;
import zzg.com.nfc.weiget.DividerItemDecoration2;
import zzg.com.nfc.weiget.InputDialog;

public class RegcardActivity extends BaseActivity {

    private static final String TAG = RegcardActivity.class.getSimpleName();
    private TextView mText;
    private RecyclerView recylerView;
    private RelativeLayout rel_layout;
    private String cardNum = null;

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private int mCount = 0;
    private int nowType = 0;
    private HomeAdapter adaptoor;

    @Override
    protected void setTitleBar() {
        titleBar.getTitle().setText("订单列表");
        titleBar.getLeft_button().setText("切换用户");
        titleBar.getRight_button().setText("录入卡");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.d(TAG,"onNewIntent--------------");
        String intentActionStr = intent.getAction();// 获取到本次启动的action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intentActionStr)// NDEF类型
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(intentActionStr)// 其他类型
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(intentActionStr)) {// 未知类型

            // 在intent中读取Tag id
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] bytesId = tag.getId();// 获取id数组
            String info = "";
            info += ByteArrayToHexString(bytesId);
            cardNum = info;
            if(nowType == 1){
                getOrder(cardNum);
            }else {
                mText.setText("卡号:  " + info);
            }
            Log.d(TAG,"onNewIntent-----------info---"+info);
        }
    }

    //16进制转10进制
    public static int HexToInt(String strHex){
        int nResult = 0;
        if ( !IsHex(strHex) )
            return nResult;
        String str = strHex.toUpperCase();
        if ( str.length() > 2 ){
            if ( str.charAt(0) == '0' && str.charAt(1) == 'X' ){
                str = str.substring(2);
            }
        }
        int nLen = str.length();
        for ( int i=0; i<nLen; ++i ){
            char ch = str.charAt(nLen-i-1);
            try {
                nResult += (GetHex(ch)*GetPower(16, i));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return nResult;
    }

    //计算16进制对应的数值
    public static int GetHex(char ch) throws Exception{
        if ( ch>='0' && ch<='9' )
            return (int)(ch-'0');
        if ( ch>='a' && ch<='f' )
            return (int)(ch-'a'+10);
        if ( ch>='A' && ch<='F' )
            return (int)(ch-'A'+10);
        throw new Exception("error param");
    }

    //计算幂
    public static int GetPower(int nValue, int nCount) throws Exception{
        if ( nCount <0 )
            throw new Exception("nCount can't small than 1!");
        if ( nCount == 0 )
            return 1;
        int nSum = 1;
        for ( int i=0; i<nCount; ++i ){
            nSum = nSum*nValue;
        }
        return nSum;
    }
    //判断是否是16进制数
    public static boolean IsHex(String strHex){
        int i = 0;
        if ( strHex.length() > 2 ){
            if ( strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x') ){
                i = 2;
            }
        }
        for ( ; i<strHex.length(); ++i ){
            char ch = strHex.charAt(i);
            if ( (ch>='0' && ch<='9') || (ch>='A' && ch<='F') || (ch>='a' && ch<='f') )
                continue;
            return false;
        }
        return true;
    }



    //转换法2   格式为  ABCD1234 字母大写
    public static  String ByteArrayToHexString(byte[] bytesId) {   //Byte数组转换为16进制字符串
        // TODO 自动生成的方法存根
        int i, j, in;
        String[] hex = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"
        };
        String output = "";

        for (j = 0; j < bytesId.length; ++j) {
            in = bytesId[j] & 0xff;
            i = (in >> 4) & 0x0f;
            output += hex[i];
            i = in & 0x0f;
            output += hex[i];
        }
        return output;
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableForegroundDispatch();
    }

    private void enableForegroundDispatch() {
        // TODO 自动生成的方法存根
        if (mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent,
                    mFilters, mTechLists);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableForegroundDispatch();
    }

    private void disableForegroundDispatch() {
        // TODO 自动生成的方法存根
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_regcard;
    }

    @Override
    protected void initView(Bundle var1) {
        mText = (TextView)findViewById(R.id.text) ;
        recylerView = (RecyclerView)findViewById(R.id.id_recyclerview);
         adaptoor = new HomeAdapter(R.layout.order_item,null);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_empty,null);
        adaptoor.setEmptyView(view);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setAdapter(adaptoor);
        rel_layout =(RelativeLayout)findViewById(R.id.rel_layout);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // Setup an intent filter for all MIME based dispatches
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        try {
            ndef.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        mFilters = new IntentFilter[] { ndef, };

        // 根据标签类型设置
        mTechLists = new String[][] { new String[] { NfcA.class.getName() } };
        titleBar.getLeft_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setIp();
                finish();
            }
        });
        titleBar.getRight_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nowType == 1){
                    nowType =2;
                    titleBar.getTitle().setText("磁卡录入");
                    titleBar.setRight_button_text("返回订单");
                    recylerView.setVisibility(View.GONE);
                    rel_layout.setVisibility(View.VISIBLE);
                    mText.setText("请把卡贴近手持设备背部" );
                }else if(nowType == 2){
                    nowType =1;
                    titleBar.setRight_button_text("录入卡");
                    titleBar.getTitle().setText("客户订单");
                    recylerView.setVisibility(View.VISIBLE);
                    rel_layout.setVisibility(View.GONE);
                }else if(nowType == 3){
                    nowType =0;
                    titleBar.getTitle().setText("订单列表");
                    titleBar.setRight_button_text("录入卡");
                    cardNum = "";
                    getOrder("");
                }
            }
        });
        recylerView.setVisibility(View.GONE);
        getOrder("");
        getAllmessageThread();
    }

    public void getOrder(String  cardKey){
        if(TextUtils.isEmpty(cardKey)){
            LoginService.getLoginService(this).getAllOrders().subscribe(new MySubscriber(this));
        }else {
            LoginService.getLoginService(this).getorders(cardKey).subscribe(new MySubscriber(this));
        }
    }

    class MySubscriber extends BaseSubscriber<List<OrderDetailsResponse>>{

        public MySubscriber(BaseActivity context) {
            super(context);
        }

        @Override
        protected void onError(APIException ex) {
            showMsg(ex.getMessage());
        }

        @Override
        public void onNext(List<OrderDetailsResponse> orderDetailsResponses) {
            if(nowType == 1){
                nowType = 3;
                titleBar.getTitle().setText("客户订单");
                titleBar.getRight_button().setText("所有订单");
            }else if(nowType == 0){
                nowType = 1;
            }
            adaptoor.replaceData(orderDetailsResponses);
            recylerView.setVisibility(View.VISIBLE);

            final DividerItemDecoration2 dividerItemDecoration=new DividerItemDecoration2(RegcardActivity.this);
            dividerItemDecoration.setOnlySetItemOffsetsButNoDraw(true);
            recylerView.addItemDecoration(dividerItemDecoration);
            if(!TextUtils.isEmpty(cardNum)){
                adaptoor.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        OrderDetailsResponse orderDetailsResponse = (OrderDetailsResponse) adapter.getItem(position);
                        setIp(orderDetailsResponse);
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void setIp(OrderDetailsResponse orderDetailsResponse) {
        InputDialog inputDialog = new InputDialog.Builder(this)
                .setTitle("请输入支付密码：")
                .setInputDefaultText("")
                .setInputMaxWords(200)
                .setInputHint("密码")
                .setPositiveButton("确定", new InputDialog.ButtonActionListener() {
                    @Override
                    public void onClick(CharSequence inputText) {
                            LoginService.getLoginService(RegcardActivity.this).pay(new PayRequest(cardNum,orderDetailsResponse.getOrderCode(),inputText.toString())).subscribe(new BaseSubscriber<PayResponse>(RegcardActivity.this) {
                                @Override
                                protected void onError(APIException ex) {
                                            showMsg(ex.getMessage());
                                }

                                @Override
                                public void onNext(PayResponse payResponse) {
                                        showMsg("支付成功");
                                }
                            });
                    }
                })
                .setNegativeButton("取消", new InputDialog.ButtonActionListener() {
                    @Override
                    public void onClick(CharSequence inputText) {
                        // TODO
                    }
                })
                .setOnCancelListener(new InputDialog.OnCancelListener() {
                    @Override
                    public void onCancel(CharSequence inputText) {
                        // TODO
                    }
                })
                .interceptButtonAction(new InputDialog.ButtonActionIntercepter() { // 拦截按钮行为
                    @Override
                    public boolean onInterceptButtonAction(int whichButton, CharSequence inputText) {
                        if ("/sdcard/my".equals(inputText) && whichButton == DialogInterface.BUTTON_POSITIVE) {
                            // TODO 此文件夹已存在，在此做相应的提示处理
                            // 以及return true拦截此按钮默认行为
                            return true;
                        }
                        return false;
                    }
                })
                .show();
    }

    public void submit(View view){
        if(TextUtils.isEmpty(cardNum)){
            showMsg("请先刷卡");
            return;
        }
//        getOrder(cardNum);
        LoginService.getLoginService(this).regCard(new RegcardRequest(cardNum)).subscribe(new BaseSubscriber<RegcardResponse>(this) {
            @Override
            protected void onError(APIException ex) {
                        showMsg(ex.getMessage());
            }

            @Override
            public void onNext(RegcardResponse regcardResponse) {
                showMsg("录入成功");
                mText.setText("请把卡贴近手持设备背部" );
                cardNum = "";
            }
        });
    }

    private void getAllmessageThread(){
        Observable.interval(1, TimeUnit.MINUTES).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        allMessageHttp();
                    }
                });

    }

    private void allMessageHttp(){
        LoginService.getLoginService(RegcardActivity.this).getAllMessage().subscribe(new BaseSubscriber<List<AllMessageResponse>>(this) {

            @Override
            public void onStart() {
//                super.onStart();
            }

            @Override
            protected void onError(APIException ex) {

            }

            @Override
            public void onNext(List<AllMessageResponse> allMessageResponses) {
                for (AllMessageResponse allMessageResponse : allMessageResponses) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("18974931832", null, allMessageResponse.getContent(), null, null);
                    sendMessageHttp(allMessageResponse);
                }
            }
        });
    }

    private void sendMessageHttp(AllMessageResponse allMessageResponse) {
        LoginService.getLoginService(RegcardActivity.this).messageSend(new MessageSendRequest(allMessageResponse.getManager())).subscribe(new BaseSubscriber<RegcardResponse>(this) {

            @Override
            public void onStart() {
//                            super.onStart();
            }

            @Override
            protected void onError(APIException ex) {

            }

            @Override
            public void onNext(RegcardResponse regcardResponse) {

            }
        });
    }

}
