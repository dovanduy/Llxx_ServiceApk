package com.llxx.socket;

import org.json.JSONObject;

import com.llxx.client.command.CommandClick;
import com.llxx.client.wrap.ClientWrap;
import com.llxx.service.R;
import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;
import com.llxx.socket.wrap.Ll_MessageListener;
import com.llxx.socket.wrap.bean.Ll_Message;
import com.llxx.utils.AccessibilityUtils;
import com.llxx.utils.BinderUtils;
import com.llxx.utils.ToastUtil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends Activity implements OnClickListener, Ll_MessageListener
{
    static final String TAG = "MainActivity";
    BinderUtils mBinderUtils;
    ClientWrap mClientWrap;

    MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mMaterialDialog = new MaterialDialog(this);

        mBinderUtils = new BinderUtils(getApplicationContext());
        mBinderUtils.bind();

        mClientWrap = new ClientWrap(this);
        new Thread(mClientWrap).start();

        setContentView(R.layout.socket_control_layout);

        findViewById(R.id.open_toast).setOnClickListener(this);
        findViewById(R.id.start_second_page).setOnClickListener(this);
        findViewById(R.id.start_fuzhu_fuwu).setOnClickListener(this);
        findViewById(R.id.start_dialog).setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (!AccessibilityUtils.isAccessibilitySettingsOn(getApplicationContext()))
        {
            showDialog();
        }
    }

    int start = 0;

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.start_fuzhu_fuwu:
            try
            {
                Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
                startActivity(intent);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            break;

        case R.id.open_toast:
            try
            {
                ToastUtil.showToast(getApplicationContext(), "show toast");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            break;

        case R.id.start_second_page:
            {
                startActivity(new Intent(this, SecondActivity.class));
            }
            break;

        case R.id.start_dialog:
            {
                showDialog();
            }
        default:
            break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mClientWrap.stop();
    }

    @Override
    public void onMessage(Ll_ClientSocketWrap wrap, Ll_Message message)
    {
        // Ll_Loger.d(TAG, "message->" + message.getMessage());
    }

    void showDialog()
    {
        if (mMaterialDialog != null)
        {
            mMaterialDialog.setTitle("辅助服务没打开")
                    .setMessage("辅助服务用来模拟点击事件，获取界面控件，打开使软件正常运行\r\n1.选择AutoTestService\r\n2.选择打开")
                    //mMaterialDialog.setBackgroundResource(R.drawable.background);
                    .setPositiveButton("打开", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            mMaterialDialog.dismiss();
                            try
                            {
                                Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
                                startActivity(intent);

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }).setNegativeButton("退出", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            mMaterialDialog.dismiss();
                            System.exit(0);
                        }
                    }).setCanceledOnTouchOutside(true)
                    // You can change the message anytime.
                    // mMaterialDialog.setTitle("提示");
                    .setOnDismissListener(new DialogInterface.OnDismissListener()
                    {
                        @Override
                        public void onDismiss(DialogInterface dialog)
                        {
                        }
                    }).show();
        }
    }

    /*
     *  
     com.android.settings.AccessibilitySettings 辅助功能设置
     　　com.android.settings.ActivityPicker 选择活动
     　　com.android.settings.ApnSettings APN设置
     　　com.android.settings.ApplicationSettings 应用程序设置
     　　com.android.settings.BandMode 设置GSM/UMTS波段
     　　com.android.settings.BatteryInfo 电池信息
     　　com.android.settings.DateTimeSettings 日期和坝上旅游网时间设置
     　　com.android.settings.DateTimeSettingsSetupWizard 日期和时间设置
     　　com.android.settings.DevelopmentSettings 应用程序设置=》开发设置
     　　com.android.settings.DeviceAdminSettings 设备管理器
     　　com.android.settings.DeviceInfoSettings 关于手机
     　　com.android.settings.Display 显示——设置显示字体大小及预览
     　　com.android.settings.DisplaySettings 显示设置
     　　com.android.settings.DockSettings 底座设置
     　　com.android.settings.IccLockSettings SIM卡锁定设置
     　　com.android.settings.InstalledAppDetails 语言和键盘设置
     　　com.android.settings.LanguageSettings 语言和键盘设置
     　　com.android.settings.LocalePicker 选择手机语言
     　　com.android.settings.LocalePickerInSetupWizard 选择手机语言
     　　com.android.settings.ManageApplications 已下载（安装）软件列表
     　　com.android.settings.MasterClear 恢复出厂设置
     　　com.android.settings.MediaFormat 格式化手机闪存
     　　com.android.settings.PhysicalKeyboardSettings 设置键盘
     　　com.android.settings.PrivacySettings 隐私设置
     　　com.android.settings.ProxySelector 代理设置
     　　com.android.settings.RadioInfo 手机信息
     　　com.android.settings.RunningServices 正在运行的程序（服务）
     　　com.android.settings.SecuritySettings 位置和安全设置
     　　com.android.settings.Settings 系统设置
     　　com.android.settings.SettingsSafetyLegalActivity 安全信息
     　　com.android.settings.SoundSettings 声音设置
     　　com.android.settings.TestingSettings 测试——显示手机信息、电池信息、使用情况统计、Wifi information、服务信息
     　　com.android.settings.TetherSettings 绑定与便携式热点
     　　com.android.settings.TextToSpeechSettings 文字转语音设置
     　　com.android.settings.UsageStats 使用情况统计
     　　com.android.settings.UserDictionarySettings 用户词典
     　　com.android.settings.VoiceInputOutputSettings 语音输入与输出设置
     　　com.android.settings.WirelessSettings 无线和网络设置
     */
}
