package cn.ucai.superwechat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.domain.User;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatHelper;
import cn.ucai.superwechat.utils.MFGT;

/**
 * Created by clawpo on 2017/4/5.
 */
public class FriendProfileActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    EaseTitleBar mTitleBar;
    @BindView(R.id.profile_image)
    ImageView mProfileImage;
    @BindView(R.id.tv_userinfo_nick)
    TextView mTvUserinfoNick;
    @BindView(R.id.tv_userinfo_name)
    TextView mTvUserinfoName;
    @BindView(R.id.btn_add_contact)
    Button mBtnAddContact;
    @BindView(R.id.btn_send_msg)
    Button mBtnSendMsg;
    @BindView(R.id.btn_send_video)
    Button mBtnSendVideo;
    User user = null;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_firend_profile);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mTitleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MFGT.finish(FriendProfileActivity.this);
            }
        });
    }

    private void initData() {
        user = (User) getIntent().getSerializableExtra(I.User.TABLE_NAME);
        if (user!=null){
            showUserInfo();
        }else{
            MFGT.finish(FriendProfileActivity.this);
        }
    }

    private void showUserInfo() {
        boolean isFriend = SuperWeChatHelper.getInstance().getAppContactList().containsKey(user.getMUserName());
        if (isFriend){
            SuperWeChatHelper.getInstance().saveAppContact(user);
        }
        mTvUserinfoName.setText(user.getMUserName());
        EaseUserUtils.setAppUserAvatar(FriendProfileActivity.this,user,mProfileImage);
        EaseUserUtils.setAppUserNick(user,mTvUserinfoNick);
        showFirend(isFriend);
    }

    private void showFirend(boolean isFirend){
        mBtnAddContact.setVisibility(isFirend?View.GONE:View.VISIBLE);
        mBtnSendMsg.setVisibility(isFirend?View.VISIBLE:View.GONE);
        mBtnSendVideo.setVisibility(isFirend?View.VISIBLE:View.GONE);
    }

    @OnClick(R.id.btn_add_contact)
    public void addContact(){
        boolean isConfirm = true;
        if (isConfirm){
            //发送验证消息
            MFGT.gotoSendAddFirend(FriendProfileActivity.this,user.getMUserName());
        }else{
            //直接添加为好友
        }
    }
}