package com.wemolian.app;

import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.chat.EMMessage.ChatType;
import com.wemolian.app.activity.BaseActivity;
import com.wemolian.app.adapter.MessageAdapter;
import com.wemolian.app.widget.PasteEditText;
import com.wemolian.app.wml.others.LoadDataFromServer;
import com.wemolian.app.wml.others.LoadDataFromServer.DataCallBack;

import org.json.JSONException;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Data;
import android.util.Log;

public class TestActivity extends BaseActivity {

	// private MessageAdapter adapter = new MessageAdapter(this, "test", 1);
	// private PasteEditText mEditTextContent = (PasteEditText)
	// findViewById(R.id.et_sendmessage);
	// private ListView listView = (ListView) findViewById(R.id.list);
	private EMConversation conversation;

	TextView testTextView;
	EditText inputText;
	Button login;
	Button sendMes;
	Button getContacts;
	Button getBuild;

	// String url = "http://192.168.0.114:8080/wemolian/message/sendMessage.do";
	String url = "http://foliager.java.jspee.net/select/selectAdmin.zy";

	// String url = "http://foliager.java.jspee.net/select/selectById.zy";
	JSONArray jsData;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.test_activity);
		initView();

		testTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(getApplicationContext(), "测试",
						Toast.LENGTH_SHORT).show();
				Map<String, String> map = new HashMap<String, String>();
				map.put("messageBody", inputText.getText().toString());
				map.put("userEName", "zhangyun1");
				map.put("userCName", "张云1");
				map.put("userSex", "男");
				LoadDataFromServer load = new LoadDataFromServer(
						TestActivity.this, url, map);
				load.getData(new DataCallBack() {
					@Override
					public void onDataCallBack(JSONObject data) {

						if (data != null && data.get("data") != null) {
							// JSONObject jsonObj =
							// data.getJSONArray("data").getJSONObject(0);
							// int id = jsonObj.getInteger("id");
							// int addData = jsonObj.getInteger("addData");
							// int delData = jsonObj.getInteger("delData");
							// int editData = jsonObj.getInteger("editData");
							// String name = jsonObj.getString("name");
							// String pwd = (String) jsonObj.get("pwd");
							// int selectData =
							// jsonObj.getInteger("selectData");

							System.out.println("测试请求返回的数据集是>>>>>>>>>" + data);
							// System.out.println("测试请求返回的数据集是1>>>>>>>>>"+jsonObj.toString());
							// System.out.println("测试请求返回的数据是2>>>>>>>>>"+"id:"+id+","+"addData:"+addData+","+"delData:"+delData+","+"editData:"+editData+","+"name:"+name+","+"pwd:"+pwd+","+"selectData:"+selectData);
						}

					}
				});
			}
		});

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "测试",
						Toast.LENGTH_SHORT).show();
				Map<String, String> map = new HashMap<String, String>();
				map.put("messageBody", inputText.getText().toString());
				map.put("userEName", "zhangyun1");
				map.put("userCName", "张云1");
				map.put("userSex", "男");
				LoadDataFromServer load = new LoadDataFromServer(
						TestActivity.this, url, map);
				load.getData(new DataCallBack() {

					@Override
					public void onDataCallBack(JSONObject data) {
						if (data != null && data.get("data") != null) {
							System.out.println("测试请求返回的数据集是>>>>>>>>>" + data);
						}
					}
				});
			}
		});
		sendMes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EMMessage message = EMMessage
						.createSendMessage(EMMessage.Type.TXT);
				TextMessageBody txtBody = new TextMessageBody(inputText
						.getText().toString());
				// 设置消息body
				message.addBody(txtBody);
				// 设置要发给谁,用户username或者群聊groupid
				message.setReceipt("11242904");
				// message.setAttribute("toUserNick", toUserNick);
				// message.setAttribute("toUserAvatar", toUserAvatar);
				message.setAttribute("useravatar", 0);
				message.setAttribute("usernick", "adawd");
				// 把messgage加到conversation中
				conversation = EMChatManager.getInstance().getConversation(
						"11242979");
				Log.i("message", message.toString());
				conversation.addMessage(message);
				// 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
				// adapter.refresh();
				// listView.setSelection(listView.getCount() - 1);
				// mEditTextContent.setText("");

				setResult(RESULT_OK);

			}
		});

		getContacts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				getContacts();

			}
		});
		
		getBuild.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Build bd = new Build();
		        Log.i("info", Build.MODEL + bd.MODEL+":"+bd.DEVICE+":"+bd.PRODUCT);
			}
		});

	}


	JSONObject contactData;
	JSONObject jsonObject;

	public String getContacts() {

		// 获得通讯录信息 ，URI是ContactsContract.Contacts.CONTENT_URI

		contactData = new JSONObject();
		String mimetype = "";
		int oldrid = -1;
		int contactId = -1;
		Cursor cursor = getContentResolver().query(Data.CONTENT_URI, null,
				null, null, Data.RAW_CONTACT_ID);
		int numm = 0;
		while (cursor.moveToNext()) {
			contactId = cursor.getInt(cursor
					.getColumnIndex(Data.RAW_CONTACT_ID));
			if (oldrid != contactId) {
				jsonObject = new JSONObject();
				contactData.put("contact" + numm, jsonObject);
				numm++;
				oldrid = contactId;
			}

			// 取得mimetype类型
			mimetype = cursor.getString(cursor.getColumnIndex(Data.MIMETYPE));
			// 获得通讯录中每个联系人的ID
			// 获得通讯录中联系人的名字
			if (StructuredName.CONTENT_ITEM_TYPE.equals(mimetype)) {
				// String display_name =
				// cursor.getString(cursor.getColumnIndex(StructuredName.DISPLAY_NAME));
				String prefix = cursor.getString(cursor
						.getColumnIndex(StructuredName.PREFIX));
				jsonObject.put("prefix", prefix);
				String firstName = cursor.getString(cursor
						.getColumnIndex(StructuredName.FAMILY_NAME));
				jsonObject.put("firstName", firstName);
				String middleName = cursor.getString(cursor
						.getColumnIndex(StructuredName.MIDDLE_NAME));
				jsonObject.put("middleName", middleName);
				String lastname = cursor.getString(cursor
						.getColumnIndex(StructuredName.GIVEN_NAME));
				jsonObject.put("lastname", lastname);
				String suffix = cursor.getString(cursor
						.getColumnIndex(StructuredName.SUFFIX));
				jsonObject.put("suffix", suffix);
				String phoneticFirstName = cursor.getString(cursor
						.getColumnIndex(StructuredName.PHONETIC_FAMILY_NAME));
				jsonObject.put("phoneticFirstName", phoneticFirstName);
				String phoneticMiddleName = cursor.getString(cursor
						.getColumnIndex(StructuredName.PHONETIC_MIDDLE_NAME));
				jsonObject.put("phoneticMiddleName", phoneticMiddleName);
				String phoneticLastName = cursor.getString(cursor
						.getColumnIndex(StructuredName.PHONETIC_GIVEN_NAME));
				jsonObject.put("phoneticLastName", phoneticLastName);
			}
			// 获取电话信息
			if (Phone.CONTENT_ITEM_TYPE.equals(mimetype)) {
				// 取出电话类型
				int phoneType = cursor
						.getInt(cursor.getColumnIndex(Phone.TYPE));
				// 手机
				if (phoneType == Phone.TYPE_MOBILE) {
					String mobile = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("mobile", mobile);
				}
				// 住宅电话
				if (phoneType == Phone.TYPE_HOME) {
					String homeNum = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("homeNum", homeNum);
				}
				// 单位电话
				if (phoneType == Phone.TYPE_WORK) {
					String jobNum = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("jobNum", jobNum);
				}
				// 单位传真
				if (phoneType == Phone.TYPE_FAX_WORK) {
					String workFax = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("workFax", workFax);
				}
				// 住宅传真
				if (phoneType == Phone.TYPE_FAX_HOME) {
					String homeFax = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("homeFax", homeFax);
				}
				// 寻呼机
				if (phoneType == Phone.TYPE_PAGER) {
					String pager = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("pager", pager);
				}
				// 回拨号码
				if (phoneType == Phone.TYPE_CALLBACK) {
					String quickNum = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("quickNum", quickNum);
				}
				// 公司总机
				if (phoneType == Phone.TYPE_COMPANY_MAIN) {
					String jobTel = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("jobTel", jobTel);
				}
				// 车载电话
				if (phoneType == Phone.TYPE_CAR) {
					String carNum = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("carNum", carNum);
				}
				// ISDN
				if (phoneType == Phone.TYPE_ISDN) {
					String isdn = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("isdn", isdn);
				}
				// 总机
				if (phoneType == Phone.TYPE_MAIN) {
					String tel = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("tel", tel);
				}
				// 无线装置
				if (phoneType == Phone.TYPE_RADIO) {
					String wirelessDev = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("wirelessDev", wirelessDev);
				}
				// 电报
				if (phoneType == Phone.TYPE_TELEX) {
					String telegram = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("telegram", telegram);
				}
				// TTY_TDD
				if (phoneType == Phone.TYPE_TTY_TDD) {
					String tty_tdd = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("tty_tdd", tty_tdd);
				}
				// 单位手机
				if (phoneType == Phone.TYPE_WORK_MOBILE) {
					String jobMobile = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("jobMobile", jobMobile);
				}
				// 单位寻呼机
				if (phoneType == Phone.TYPE_WORK_PAGER) {
					String jobPager = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("jobPager", jobPager);
				}
				// 助理
				if (phoneType == Phone.TYPE_ASSISTANT) {
					String assistantNum = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("assistantNum", assistantNum);
				}
				// 彩信
				if (phoneType == Phone.TYPE_MMS) {
					String mms = cursor.getString(cursor
							.getColumnIndex(Phone.NUMBER));
					jsonObject.put("mms", mms);
				}
			}
			// }
			// 查找email地址
			if (Email.CONTENT_ITEM_TYPE.equals(mimetype)) {
				// 取出邮件类型
				int emailType = cursor
						.getInt(cursor.getColumnIndex(Email.TYPE));

				// 住宅邮件地址
				if (emailType == Email.TYPE_CUSTOM) {
					String homeEmail = cursor.getString(cursor
							.getColumnIndex(Email.DATA));
					jsonObject.put("homeEmail", homeEmail);
				}

				// 住宅邮件地址
				else if (emailType == Email.TYPE_HOME) {
					String homeEmail = cursor.getString(cursor
							.getColumnIndex(Email.DATA));
					jsonObject.put("homeEmail", homeEmail);
				}
				// 单位邮件地址
				if (emailType == Email.TYPE_CUSTOM) {
					String jobEmail = cursor.getString(cursor
							.getColumnIndex(Email.DATA));
					jsonObject.put("jobEmail", jobEmail);
				}

				// 单位邮件地址
				else if (emailType == Email.TYPE_WORK) {
					String jobEmail = cursor.getString(cursor
							.getColumnIndex(Email.DATA));
					jsonObject.put("jobEmail", jobEmail);
				}
				// 手机邮件地址
				if (emailType == Email.TYPE_CUSTOM) {
					String mobileEmail = cursor.getString(cursor
							.getColumnIndex(Email.DATA));
					jsonObject.put("mobileEmail", mobileEmail);
				}

				// 手机邮件地址
				else if (emailType == Email.TYPE_MOBILE) {
					String mobileEmail = cursor.getString(cursor
							.getColumnIndex(Email.DATA));
					jsonObject.put("mobileEmail", mobileEmail);
				}
			}
			// 查找event地址
			if (Event.CONTENT_ITEM_TYPE.equals(mimetype)) {
				// 取出时间类型
				int eventType = cursor
						.getInt(cursor.getColumnIndex(Event.TYPE));
				// 生日
				if (eventType == Event.TYPE_BIRTHDAY) {
					String birthday = cursor.getString(cursor
							.getColumnIndex(Event.START_DATE));
					jsonObject.put("birthday", birthday);
				}
				// 周年纪念日
				if (eventType == Event.TYPE_ANNIVERSARY) {
					String anniversary = cursor.getString(cursor
							.getColumnIndex(Event.START_DATE));
					jsonObject.put("anniversary", anniversary);
				}
			}
			// 即时消息
			if (Im.CONTENT_ITEM_TYPE.equals(mimetype)) {
				// 取出即时消息类型
				int protocal = cursor
						.getInt(cursor.getColumnIndex(Im.PROTOCOL));
				if (Im.TYPE_CUSTOM == protocal) {
					String workMsg = cursor.getString(cursor
							.getColumnIndex(Im.DATA));
					jsonObject.put("workMsg", workMsg);
				}

				else if (Im.PROTOCOL_MSN == protocal) {
					String workMsg = cursor.getString(cursor
							.getColumnIndex(Im.DATA));
					jsonObject.put("workMsg", workMsg);
				}
				if (Im.PROTOCOL_QQ == protocal) {
					String instantsMsg = cursor.getString(cursor
							.getColumnIndex(Im.DATA));
					jsonObject.put("instantsMsg", instantsMsg);
				}
			}
			// 获取备注信息
			if (Note.CONTENT_ITEM_TYPE.equals(mimetype)) {
				String remark = cursor.getString(cursor
						.getColumnIndex(Note.NOTE));
				jsonObject.put("remark", remark);
			}
			// 获取昵称信息
			if (Nickname.CONTENT_ITEM_TYPE.equals(mimetype)) {
				String nickName = cursor.getString(cursor
						.getColumnIndex(Nickname.NAME));
				jsonObject.put("nickName", nickName);
			}
			// 获取组织信息
			if (Organization.CONTENT_ITEM_TYPE.equals(mimetype)) {
				// 取出组织类型
				int orgType = cursor.getInt(cursor
						.getColumnIndex(Organization.TYPE));
				// 单位
				if (orgType == Organization.TYPE_CUSTOM) {
					// if (orgType == Organization.TYPE_WORK) {
					String company = cursor.getString(cursor
							.getColumnIndex(Organization.COMPANY));
					jsonObject.put("company", company);
					String jobTitle = cursor.getString(cursor
							.getColumnIndex(Organization.TITLE));
					jsonObject.put("jobTitle", jobTitle);
					String department = cursor.getString(cursor
							.getColumnIndex(Organization.DEPARTMENT));
					jsonObject.put("department", department);
				}
			}
			// 获取网站信息
			if (Website.CONTENT_ITEM_TYPE.equals(mimetype)) {
				// 取出组织类型
				int webType = cursor
						.getInt(cursor.getColumnIndex(Website.TYPE));
				// 主页
				if (webType == Website.TYPE_CUSTOM) {
					String home = cursor.getString(cursor
							.getColumnIndex(Website.URL));
					jsonObject.put("home", home);
				}
				// 主页
				else if (webType == Website.TYPE_HOME) {
					String home = cursor.getString(cursor
							.getColumnIndex(Website.URL));
					jsonObject.put("home", home);
				}

				// 个人主页
				if (webType == Website.TYPE_HOMEPAGE) {
					String homePage = cursor.getString(cursor
							.getColumnIndex(Website.URL));
					jsonObject.put("homePage", homePage);
				}
				// 工作主页
				if (webType == Website.TYPE_WORK) {
					String workPage = cursor.getString(cursor
							.getColumnIndex(Website.URL));
					jsonObject.put("workPage", workPage);
				}
			}
			// 查找通讯地址
			if (StructuredPostal.CONTENT_ITEM_TYPE.equals(mimetype)) {
				// 取出邮件类型
				int postalType = cursor.getInt(cursor
						.getColumnIndex(StructuredPostal.TYPE));
				// 单位通讯地址
				if (postalType == StructuredPostal.TYPE_WORK) {
					String street = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.STREET));
					jsonObject.put("street", street);
					String ciry = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.CITY));
					jsonObject.put("ciry", ciry);
					String box = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.POBOX));
					jsonObject.put("box", box);
					String area = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.NEIGHBORHOOD));
					jsonObject.put("area", area);
					String state = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.REGION));
					jsonObject.put("state", state);
					String zip = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.POSTCODE));
					jsonObject.put("zip", zip);
					String country = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.COUNTRY));
					jsonObject.put("country", country);
				}
				// 住宅通讯地址
				if (postalType == StructuredPostal.TYPE_HOME) {
					String homeStreet = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.STREET));
					jsonObject.put("homeStreet", homeStreet);
					String homeCity = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.CITY));
					jsonObject.put("homeCity", homeCity);
					String homeBox = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.POBOX));
					jsonObject.put("homeBox", homeBox);
					String homeArea = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.NEIGHBORHOOD));
					jsonObject.put("homeArea", homeArea);
					String homeState = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.REGION));
					jsonObject.put("homeState", homeState);
					String homeZip = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.POSTCODE));
					jsonObject.put("homeZip", homeZip);
					String homeCountry = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.COUNTRY));
					jsonObject.put("homeCountry", homeCountry);
				}
				// 其他通讯地址
				if (postalType == StructuredPostal.TYPE_OTHER) {
					String otherStreet = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.STREET));
					jsonObject.put("otherStreet", otherStreet);
					String otherCity = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.CITY));
					jsonObject.put("otherCity", otherCity);
					String otherBox = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.POBOX));
					jsonObject.put("otherBox", otherBox);
					String otherArea = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.NEIGHBORHOOD));
					jsonObject.put("otherArea", otherArea);
					String otherState = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.REGION));
					jsonObject.put("otherState", otherState);
					String otherZip = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.POSTCODE));
					jsonObject.put("otherZip", otherZip);
					String otherCountry = cursor.getString(cursor
							.getColumnIndex(StructuredPostal.COUNTRY));
					jsonObject.put("otherCountry", otherCountry);
				}
			}
		}
		cursor.close();
		Log.i("contactData", contactData.toString());
		return contactData.toString();

	}

	private void initView() {
		testTextView = (TextView) findViewById(R.id.test_tv);
		inputText = (EditText) findViewById(R.id.test_input_tv);
		sendMes = (Button) findViewById(R.id.sendMes);
		login = (Button) findViewById(R.id.login);
		getContacts = (Button) findViewById(R.id.get_contacts);
		getBuild = (Button) findViewById(R.id.get_build);
	}

}
