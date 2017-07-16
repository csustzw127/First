package com.example.sqlitetest;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button btnAdd;
	private Button btnDel;
	private Button btnAll;
	private EditText txtName;
	private EditText txtId;
	private EditText logMsg;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
		initDataBase();
		initComponent();
		

		this.btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				addOne();
			}
		});


		this.btnDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				delOne();
			}
		});
		
		this.btnAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showAll();
			}
		});
	}

	protected void showAll() {
		// TODO Auto-generated method stub
		Cursor c= db.rawQuery("select * from student", null);
		
		if(c.getCount() == 0) {
			showMessage("warning","�����κ���Ϣ");
			return;
		}
		
		StringBuilder strB = new StringBuilder();
		while(c.moveToNext()) {
			strB.append("ѧ��: " + c.getString(1) + "\n");
			strB.append("����: " + c.getString(0) + "\n");
		}
		showMessage("ѧ����Ϣ: ",strB.toString());
		return ;
	}

	protected void delOne() {
		if (txtId.getText().toString().trim().length() == 0) {
			showMessage("error", "������Ҫɾ����ѧ��");
		}
		Cursor c = db.rawQuery("select * from student where id='"+txtId.getText()+"'", null);
		if(c.moveToFirst()) {
			db.execSQL("delete from student where id='"+txtId.getText()+"'");
		} else {
			showMessage("error", "��ѧ���޶�Ӧѧ��");
			return;
		}
		showMessage("success", "ɾ���ɹ�");
	}

	

	protected void addOne() {
		if (txtId.getText().toString().trim().length() == 0
				|| txtName.getText().toString().trim().length() == 0) {
			showMessage("error", "������ѧ��������ѧ��");
			return;
		}

		String sql = "insert into student values('"
				+ txtName.getText().toString() + "','"
				+ txtId.getText().toString() + "')";

		db.execSQL(sql);
		showMessage("success","��ӳɹ� ");
		clearText();
	}

	private void clearText() {
		this.txtId.setText("");
		this.txtName.setText("");
	}

	void initDataBase() {
		db = openOrCreateDatabase("student_info", Context.MODE_PRIVATE, null);
		db.execSQL("create table if not exists student(" + "name varchar,"
				+ "id   varchar);");
	}

	void initComponent() {
		this.btnAdd = (Button) findViewById(R.id.btnAdd);
		this.btnAll = (Button) findViewById(R.id.button1);
		this.btnDel = (Button) findViewById(R.id.btnDel);
		this.txtName = (EditText) findViewById(R.id.txtName);
		this.txtId = (EditText) findViewById(R.id.txtId);
		this.logMsg = (EditText) findViewById(R.id.logMsg);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
