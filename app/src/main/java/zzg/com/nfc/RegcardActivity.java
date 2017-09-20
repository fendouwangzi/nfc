package zzg.com.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import zzg.com.nfc.net.api.LoginService;
import zzg.com.nfc.net.base.BaseSubscriber;
import zzg.com.nfc.net.exception.APIException;
import zzg.com.nfc.net.request.RegcardRequest;
import zzg.com.nfc.net.response.RegcardResponse;
import zzg.com.nfc.ui.base.BaseActivity;

public class RegcardActivity extends BaseActivity {

    private static final String TAG = RegcardActivity.class.getSimpleName();
    TextView mText;
    private String cardNum = null;

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private int mCount = 0;


    @Override
    protected void setTitleBar() {
        titleBar.setTitleMainText("标题栏神器");
//        titleBar.setTitleSubText(getSubText());
        titleBar.setRightTextDrawable(isWhite ? R.drawable.ic_menu : R.drawable.ic_menu_white);
        titleBar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setIp();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.d(TAG,"onNewIntent--------------");
        mText.setText("发现新的 Tag:  " + ++mCount + "\n");// mCount 计数
        String intentActionStr = intent.getAction();// 获取到本次启动的action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intentActionStr)// NDEF类型
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(intentActionStr)// 其他类型
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(intentActionStr)) {// 未知类型

            // 在intent中读取Tag id
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] bytesId = tag.getId();// 获取id数组
            String info = "";
            info += ByteArrayToHexString(bytesId) + "\n";
            mText.setText("标签UID:  " + "\n" + HexToInt(info));
            Log.d(TAG,"onNewIntent-----------info---"+HexToInt(info));
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
    }

    public void submit(View view){
        cardNum = "dddddd";
        LoginService.getLoginService().regCard(new RegcardRequest(cardNum)).subscribe(new BaseSubscriber<RegcardResponse>(this) {
            @Override
            protected void onError(APIException ex) {
                        showMsg(ex.getMessage());
            }

            @Override
            public void onNext(RegcardResponse regcardResponse) {

            }
        });
    }

}
