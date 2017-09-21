package com.example.wuwenxuan.changepage;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listview;
    LoadingDialog mDialog;

    private MaterialSpinner materialSpinner;
    Button btn_lastpage, btn_nextpage, btn_check;
    TextView et_datastart, et_dataend, total_page;
    EditText et_select_page;
    GasInfoAdapter adapter;

//    StoreBean storeBean;
//    private SerchGasCheckDataBean mSerchGasCheckDataBean;

    //private List<GasInfo> list_grain = new ArrayList();
    private List<GasInfo> list_temp = new ArrayList();
    private List<GasInfo> list_check = new ArrayList();
    private List<GasInfo> list_all = new ArrayList();
    private List<List<String>> list_granary = new ArrayList();
    private List<String> list_material = new ArrayList();
    //廒仓号  起止年月日
    int granary, startY = 0, startM = 0, startD = 0, endY = 0, endM = 0, endD = 0;
    int VIEW_COUNT = 12;// 每页item数目
    int Total_page;//总计页面数目
    // 用于显示页号的索引
    int Page_num = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gas_info);

        initData();
        initView();
        getTotalPage();


        setListener();
    }

    private void initData() {
//        List<String> list = new ArrayList();
        list_check.clear();
        list_material.clear();
        list_material.add("全部");
        for (int i = 0; i < 8; i++) {

            list_material.add("仓库" + (i+1));
//            list.clear();
//            list.add(i + "");
//            list.add("廒间" + i + 1);
//            list_granary.add(list);
        }


        // TODO: 2017/8/30 测试用添加表单

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());

        for (int i = 0; i < 257; i++) {
            GasInfo info = new GasInfo();
            info.setId(i+1);
            info.setAddress_id(Integer.toString(i%8+1));
            info.setDate(date);
            info.setType(i%5+"");
            list_all.add(info);
        }
        list_check.addAll(list_all);
        getList();




    }

    /**
     * 获取气体信息
     */
//    private void getSerchGasCheckDataBean(String id, String startTime, String endTime) {
//        if (mDialog == null) {
//            mDialog = LoadingDialog.createDialog(MainActivity.this());
//        }
//        mDialog.show();
//        Map<String, String> header = new HashMap<String, String>();
//        Map<String, String> params = new HashMap<String, String>();
//
//        header.put("Cookie", "JSESSIONID=" + BapAppAppliction.JSESSIONID + "; " +
//                "CASTGC=" + BapAppAppliction.CASTGC);
//        header.put("Authorization", "CASTGC " + BapAppAppliction.CASTGC);
//        header.put("USER-AGENT", "Linux; U; Android");
//        params.put("storePlaceId", id);
//        if (!startTime.equals("0_0_0 00:00:00")) {
//            params.put("startTime", startTime);
//        }
//        if (!endTime.equals("0_0_0 00:00:00")) {
//            params.put("endTime", endTime);
//        }
//        /*if (!startTime.equals("0_0_0 00:00:00")&&!endTime.equals("0_0_0 00:00:00")){
//            url = CacheUtils.getString(MainActivity.this().getBaseContext(), "host")+"/houseWork/grainMonitor/grainMonitor/appSerchGasCheckData.action?" +
//                    "startTime="+startTime+"&endTime="+endTime+"&storePlaceId="+id;
//        }else if (startTime.equals("0_0_0 00:00:00")&&endTime.equals("0_0_0 00:00:00")){
//            url = CacheUtils.getString(MainActivity.this().getBaseContext(), "host")+"/houseWork/grainMonitor/grainMonitor/appSerchGasCheckData.action?" +"storePlaceId="+id;
//        }else {
//            url = CacheUtils.getString(MainActivity.this().getBaseContext(), "host")+"/houseWork/grainMonitor/grainMonitor/appSerchGasCheckData.action?" +"storePlaceId="+id;
//        }*/
//        String url = CacheUtils.getString(MainActivity.this().getBaseContext(), "host") + "/houseWork/grainMonitor/grainMonitor/appSerchGasCheckData.action";
//
//        OkHttpUtils
//                .get()
//                .url(url)
//                .headers(header)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        if (mDialog.isShowing()) {
//                            mDialog.dismiss();
//                        }
//                        BapToast.show(MainActivity.this(), "气体信息请求错误！");
//                        if (list_check.size() == 0) {
//                            btn_lastpage.setVisibility(View.INVISIBLE);
//                            btn_nextpage.setVisibility(View.INVISIBLE);
//                            et_select_page.setVisibility(View.INVISIBLE);
//                            total_page.setVisibility(View.INVISIBLE);
//                        }
//
//                        // TODO: 2017/9/19  测试数据
//                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
//                        String date = sDateFormat.format(new java.util.Date());
//                        list_check.clear();
//                        for (int i = 1; i < 57; i++) {
//                            GasInfo info = new GasInfo();
//                            info.setId(i);
//                            info.setAddress_id(i+"");
//                            info.setDate(date);
//                            info.setType("zidong");
//                            list_check.add(info);
//                        }
//                        getTotalPage();
//                        getList();
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        if (mDialog.isShowing()) {
//                            mDialog.dismiss();
//                        }
//                        Gson gson = new Gson();
//                        mSerchGasCheckDataBean = gson.fromJson(response, SerchGasCheckDataBean.class);
//                        if (mSerchGasCheckDataBean.getStatus().equals("0")) {
//                            list_check.clear();
//                            for (int i = 0; i < mSerchGasCheckDataBean.getCheckData().size(); i++) {
//                                GasInfo gasInfo = new GasInfo();
//                                gasInfo.setId(i + 1);
//                                gasInfo.setDate((mSerchGasCheckDataBean.getCheckData().get(i)).get(2));
//                                gasInfo.setAddress_id((mSerchGasCheckDataBean.getCheckData().get(i)).get(1));
//                                gasInfo.setType(getCheckType((mSerchGasCheckDataBean.getCheckData().get(i)).get(3)));
//                                list_check.add(gasInfo);
//                            }
//                            if (list_check.size() == 0) {
//                                btn_lastpage.setVisibility(View.INVISIBLE);
//                                btn_nextpage.setVisibility(View.INVISIBLE);
//                                et_select_page.setVisibility(View.INVISIBLE);
//                                total_page.setVisibility(View.INVISIBLE);
//                            }
//
//                            getTotalPage();
//                            getList();
//                            adapter.notifyDataSetChanged();
//                        } else if (mSerchGasCheckDataBean.getStatus().equals("1")) {
//                            BapToast.show(MainActivity.this(), "气体信息请求失败");
//                        } else if (mSerchGasCheckDataBean.getStatus().equals("2")) {
//                            BapToast.show(MainActivity.this(), "气体信息过多，请输入查询时间");
//                        }
//                    }
//                });
//
//    }

    /**
     * 获取气体的检测方式
     */
    private String getCheckType(String s) {
        String result = "";
        switch (s) {
            case "0":
                result = "";
                break;
            case "1":
                result = "自动检测";
                break;
            case "2":
                result = "手动检测";
                break;
            case "3":
                result = "定时检测";
                break;
            case "4":
                result = "循环检测";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    /**
     * 获得廒间信息
     */
//    private void getStorePlace() {
//        String cacheStorePlace = CacheUtils.getString(MainActivity.this().getBaseContext(), "storeplace");
//        if (!cacheStorePlace.equals("")) {
//            Gson gson = new Gson();
//            storeBean = gson.fromJson(cacheStorePlace, StoreBean.class);
//            for (List<String> item : storeBean.getStorePlace()) {
//                list_granary.add(item);
//                list_material.add(item.get(1));
//            }
//            materialSpinner.setItems(list_material);
//            adapter.notifyDataSetChanged();
//        }
//
//        Map<String, String> header = new HashMap<String, String>();
//        header.put("Cookie", "JSESSIONID=" + BapAppAppliction.JSESSIONID + "; " +
//                "CASTGC=" + BapAppAppliction.CASTGC);
//        header.put("Authorization", "CASTGC " + BapAppAppliction.CASTGC);
//        header.put("USER-AGENT", "Linux; U; Android");
//        String url = CacheUtils.getString(MainActivity.this().getBaseContext(), "host") + "/houseWork/grainMonitor/grainMonitor/appSerchStorePlace.action";
//        OkHttpUtils
//                .get()
//                .url(url)
//                .headers(header)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        BapToast.show(MainActivity.this(), "廒间信息请求失败！");
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        String jsonSP = CacheUtils.getString(MainActivity.this().getBaseContext(), "storeplace");
//                        Log.i("TAG", "onResponse: " + "UUUUUUUUUUUUUU" + jsonSP);
//                        try {
//                            String status = new JSONObject(response).getString("status");
//                            if (status.equals("0")) {
//                                if (jsonSP.equals("") || (!jsonSP.equals(response))) {
//                                    CacheUtils.putString(MainActivity.this().getBaseContext(), "storeplace", response);
//                                    list_granary.clear();
//                                    list_material.clear();
//                                    Gson gson = new Gson();
//                                    storeBean = gson.fromJson(response, StoreBean.class);
//                                    for (List<String> item : storeBean.getStorePlace()) {
//                                        list_granary.add(item);
//                                        list_material.add(item.get(1));
//                                    }
//                                    materialSpinner.setItems(list_material);
//                                    adapter.notifyDataSetChanged();
//                                } else {
//
//                                }
//                            } else if (status.equals("1")) {
//                                BapToast.show(MainActivity.this, "廒间信息请求失败！");
//                            } else {
//                                BapToast.show(MainActivity.this, "廒间信息请求失败！");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }

    private void setListener() {
        et_datastart.setOnClickListener(this);
        et_dataend.setOnClickListener(this);
        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                granary = position;
                Log.i("TAG", "" + granary);
            }
        });
        btn_check.setOnClickListener(this);
        btn_nextpage.setOnClickListener(this);
        btn_lastpage.setOnClickListener(this);
        /**输入完成*/
        et_select_page.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String num = et_select_page.getText().toString();
                    if (!TextUtils.isEmpty(num)) {
                        int currentNum = Integer.parseInt(num);
                        if (currentNum <= Total_page) {
                            Page_num = currentNum;
                            if (Page_num == 1) {
                                btn_lastpage.setVisibility(View.INVISIBLE);
                                btn_nextpage.setVisibility(View.VISIBLE);
                            } else {
                                btn_lastpage.setVisibility(View.VISIBLE);
                                btn_nextpage.setVisibility(View.VISIBLE);
                            }
                            if (Page_num == Total_page) {
                                btn_lastpage.setVisibility(View.VISIBLE);
                                btn_nextpage.setVisibility(View.INVISIBLE);
                            }

                            getList();
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MainActivity.this, "输入大于总页面数", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
                return false;
            }
        });
        /**失去焦点*/
        et_select_page.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    if (list_check.size() == 0) {
                        btn_lastpage.setVisibility(View.INVISIBLE);
                        btn_lastpage.setVisibility(View.INVISIBLE);
                    } else {
                        String num = et_select_page.getText().toString();
                        if (!TextUtils.isEmpty(num)) {
                            int currentNum = Integer.parseInt(num);
                            if (currentNum <= Total_page) {
                                Page_num = currentNum;
                                if (Page_num == 1) {
                                    btn_lastpage.setVisibility(View.INVISIBLE);
                                    btn_nextpage.setVisibility(View.VISIBLE);
                                } else {
                                    btn_lastpage.setVisibility(View.VISIBLE);
                                    btn_nextpage.setVisibility(View.VISIBLE);
                                }
                                if (Page_num == Total_page) {
                                    btn_lastpage.setVisibility(View.VISIBLE);
                                    btn_nextpage.setVisibility(View.INVISIBLE);
                                }

                                getList();
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(MainActivity.this, "输入大于总页面数", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            }
        });

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //startActivity(new Intent(MainActivity.this(),xxx.class));
//                // TODO: 2017/9/1  跳转
//                //Toast.makeText(MainActivity.this(), "item click", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this(), GasInfoActivity.class);
//                /**传递廒间名*/
//                intent.putExtra("roomname", (list_granary.get(granary)).get(1));
//                /**传递时间*//*
//                intent.putExtra("date",list_check.get(position).getDate());*/
//                /**记录id*/
//                intent.putExtra("id", list_check.get((granary - 1) * 8 + position).getId());
//                startActivity(intent);
//            }
//        });
    }

    private void initView() {
        et_datastart = (TextView) findViewById(R.id.et_datastart);
        et_dataend = (TextView) findViewById(R.id.et_dataend);
        // TODO: 2017/9/4 获取系统时间测试用
        /*SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());*/

        total_page = (TextView) findViewById(R.id.total_page);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_lastpage = (Button) findViewById(R.id.btn_last_page);
        btn_nextpage = (Button) findViewById(R.id.btn_next_page);


        materialSpinner = (MaterialSpinner) findViewById(R.id.granary);
        materialSpinner.setItems(list_material);

        listview = (ListView) findViewById(R.id.grain_list);
        adapter = new GasInfoAdapter(this, list_temp);
        listview.setAdapter(adapter);

        et_select_page = (EditText) findViewById(R.id.et_select_page);
        et_select_page.setText(Page_num + "");

        btn_nextpage.setVisibility(View.INVISIBLE);
        et_select_page.setVisibility(View.INVISIBLE);
        total_page.setVisibility(View.INVISIBLE);
    }

    /**
     * 截止时间选择
     */
    private void endDatePickDialog() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.e("TAG", "ed" + year + month + dayOfMonth);
                if (startY <= year && startM <= month + 1 && startD <= dayOfMonth) {
                    endY = year;
                    endM = month + 1;
                    endD = dayOfMonth;
                    Log.e("TAG", "st1" + endY + endM + endD);
                    et_dataend.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                } else {
                    Toast.makeText(MainActivity.this, "截至时间不可小于起始时间", Toast.LENGTH_SHORT).show();
                }

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
        datePickerDialog.show();
    }

    /**
     * 开始时间选择
     */
    private void beginDatePickDialog() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.e("TAG", "st" + year + month + dayOfMonth);
                if (endY >= year && endM >= month + 1 && endD >= dayOfMonth) {
                    startY = year;
                    startM = month + 1;
                    startD = dayOfMonth;
                    Log.e("TAG", "st1" + startY + startM + startD);
                    et_datastart.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                } else {
                    Toast.makeText(MainActivity.this, "起始时间不可大于截至时间", Toast.LENGTH_SHORT).show();
                }

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
        datePickerDialog.show();
    }

    /**
     * wuwenxuan
     * creat by 2017/9/4 10:50
     * 功能  获取总页数
     */
    private void getTotalPage() {
        if (list_check.size() <= VIEW_COUNT) {
            Total_page = 1;
            total_page.setVisibility(View.VISIBLE);
            btn_lastpage.setVisibility(View.INVISIBLE);
            btn_nextpage.setVisibility(View.INVISIBLE);
        } else {
            if (list_check.size() % VIEW_COUNT == 0) {
                Total_page = list_check.size() / VIEW_COUNT;
            } else {
                Total_page = 1 + list_check.size() / VIEW_COUNT;
            }
            total_page.setVisibility(View.VISIBLE);
            et_select_page.setVisibility(View.VISIBLE);
            btn_nextpage.setVisibility(View.VISIBLE);

        }
        total_page.setText(" / " + Total_page);
    }

    // TODO: 2017/8/30  翻页list_temp设置
    private void getList() {
        list_temp.clear();
        if (Page_num == Total_page) {
            for (int i = VIEW_COUNT * (Page_num - 1); i < list_check.size(); i++) {
                if (list_check.size() == 0) {
                    btn_lastpage.setVisibility(View.INVISIBLE);
                    btn_nextpage.setVisibility(View.INVISIBLE);
                    et_select_page.setVisibility(View.INVISIBLE);
                    total_page.setVisibility(View.INVISIBLE);
                } else {
                    list_temp.add(list_check.get(i));
                }
            }
        } else {

            for (int i = VIEW_COUNT * (Page_num - 1); i < VIEW_COUNT * (Page_num); i++) {
                if (list_check.size() == 0) {
                    btn_lastpage.setVisibility(View.INVISIBLE);
                    btn_nextpage.setVisibility(View.INVISIBLE);
                    et_select_page.setVisibility(View.INVISIBLE);
                    total_page.setVisibility(View.INVISIBLE);
                } else {
                    if(i<list_check.size()) {
                        list_temp.add(list_check.get(i));
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!(mDialog == null)) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mDialog = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_last_page:
                btn_nextpage.setVisibility(View.VISIBLE);
                if (Page_num <= Total_page && Page_num > 0) {
                    Page_num--;
                    Log.e("Page_num",Page_num+"");
                    if (Page_num == 1) {
                        btn_lastpage.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Page_num = 1;
                    btn_lastpage.setVisibility(View.INVISIBLE);
                }
                et_select_page.setText(Page_num + "");
                getList();
                adapter.notifyDataSetChanged();
                Log.e("TAG", "" + Page_num);


                break;
            case R.id.btn_next_page:

                btn_lastpage.setVisibility(View.VISIBLE);
                if (Page_num < Total_page && Page_num > 0) {
                    Page_num++;
                    if (Page_num == Total_page) {
                        btn_nextpage.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Page_num = Total_page;
                    btn_nextpage.setVisibility(View.INVISIBLE);

                }
                et_select_page.setText(Page_num + "");
                getList();
                adapter.notifyDataSetChanged();
                Log.e("TAG", "" + Page_num);


                break;
            case R.id.btn_check://查询按钮
                if(list_temp.size() <VIEW_COUNT) {
                    btn_lastpage.setVisibility(View.INVISIBLE);
                    btn_nextpage.setVisibility(View.INVISIBLE);
                    et_select_page.setVisibility(View.INVISIBLE);
                }
                list_check.clear();
//                list_check.addAll(list_check);

                switch (granary) {
                    case  1:
                        for(int i=0;i<list_all.size();i++){
                            GasInfo gas = new GasInfo();
                            if(list_all.get(i).getAddress_id().equals(String.valueOf(granary))) {
                                gas.setId(list_all.get(i).getId());
                                gas.setAddress_id(list_all.get(i).getAddress_id());
                                gas.setDate(list_all.get(i).getDate());
                                gas.setType(list_all.get(i).getType());
                                list_check.add(gas);
                            }
                        }
                        break;
                    case  2:
                        list_check.clear();
                        for(int i=0;i<list_all.size();i++){
                            GasInfo gas = new GasInfo();
                            if(list_all.get(i).getAddress_id().equals(String.valueOf(granary))) {
                                gas.setId(list_all.get(i).getId());
                                gas.setAddress_id(list_all.get(i).getAddress_id());
                                gas.setDate(list_all.get(i).getDate());
                                gas.setType(list_all.get(i).getType());
                                list_check.add(gas);
                            }

                        }
                        break;
                    case  3:
                        list_check.clear();
                        for(int i=0;i<list_all.size();i++){
                            GasInfo gas = new GasInfo();
                            if(list_all.get(i).getAddress_id().equals(String.valueOf(granary))) {
                                gas.setId(list_all.get(i).getId());
                                gas.setAddress_id(list_all.get(i).getAddress_id());
                                gas.setDate(list_all.get(i).getDate());
                                gas.setType(list_all.get(i).getType());
                                list_check.add(gas);
                            }
                        }
                        break;
                    case  4:
                        list_check.clear();
                        for(int i=0;i<list_all.size();i++){
                            GasInfo gas = new GasInfo();
                            if(list_all.get(i).getAddress_id().equals(String.valueOf(granary))) {
                                gas.setId(list_all.get(i).getId());
                                gas.setAddress_id(list_all.get(i).getAddress_id());
                                gas.setDate(list_all.get(i).getDate());
                                gas.setType(list_all.get(i).getType());
                                list_check.add(gas);
                            }
                        }
                        break;
                    case  5:
                        list_check.clear();
                        for(int i=0;i<list_all.size();i++){
                            GasInfo gas = new GasInfo();
                            if(list_all.get(i).getAddress_id().equals(String.valueOf(granary))) {
                                gas.setId(list_all.get(i).getId());
                                gas.setAddress_id(list_all.get(i).getAddress_id());
                                gas.setDate(list_all.get(i).getDate());
                                gas.setType(list_all.get(i).getType());
                                list_check.add(gas);
                            }
                        }
                        break;
                    case  6:
                        list_check.clear();
                        for(int i=0;i<list_all.size();i++){
                            GasInfo gas = new GasInfo();
                            if(list_all.get(i).getAddress_id().equals(String.valueOf(granary))) {
                                gas.setId(list_all.get(i).getId());
                                gas.setAddress_id(list_all.get(i).getAddress_id());
                                gas.setDate(list_all.get(i).getDate());
                                gas.setType(list_all.get(i).getType());
                                list_check.add(gas);
                            }
                        }
                        break;
                    case  7:
                        list_check.clear();
                        for(int i=0;i<list_all.size();i++){
                            GasInfo gas = new GasInfo();
                            if(list_all.get(i).getAddress_id().equals(String.valueOf(granary))) {
                                gas.setId(list_all.get(i).getId());
                                gas.setAddress_id(list_all.get(i).getAddress_id());
                                gas.setDate(list_all.get(i).getDate());
                                gas.setType(list_all.get(i).getType());
                                list_check.add(gas);
                            }
                        }
                        break;
                    case  8:
                        list_check.clear();
                        for(int i=0;i<list_all.size();i++){
                            GasInfo gas = new GasInfo();
                            if(list_all.get(i).getAddress_id().equals(String.valueOf(granary))) {
                                gas.setId(list_all.get(i).getId());
                                gas.setAddress_id(list_all.get(i).getAddress_id());
                                gas.setDate(list_all.get(i).getDate());
                                gas.setType(list_all.get(i).getType());
                                list_check.add(gas);
                            }
                        }
                        break;
                    default:
                        list_check.addAll(list_all);
                        break;
                }
                Page_num = 1;
                btn_lastpage.setVisibility(View.INVISIBLE);
                btn_nextpage.setVisibility(View.VISIBLE);
                et_select_page.setText(""+1);
                Log.e("TAG", "btn_check ");
                // TODO: 2017/8/30 wwww 设置页码总数
                Log.e("Total_page", "" + Total_page);
                getList();
                getTotalPage();
                adapter.notifyDataSetChanged();
                break;
            case R.id.et_datastart:
                beginDatePickDialog();
                Log.e("TAG", "y" + startY + startM + startD);
                break;
            case R.id.et_dataend:
                endDatePickDialog();
                Log.e("TAG", "y" + endY + endM + endD);
                break;
            case R.id.btn_query:
                v.setFocusableInTouchMode(true);
                InputMethodManager imm1 = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

                imm1.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (list_check.size() == 0) {
                    btn_lastpage.setVisibility(View.INVISIBLE);
                    btn_lastpage.setVisibility(View.INVISIBLE);
                } else {
                    String num = et_select_page.getText().toString();
                    if (!TextUtils.isEmpty(num)) {
                        int currentNum = Integer.parseInt(num);
                        if (currentNum <= Total_page) {
                            Page_num = currentNum;
                            if (Page_num == 1) {
                                btn_lastpage.setVisibility(View.INVISIBLE);
                                btn_nextpage.setVisibility(View.VISIBLE);
                            } else {
                                btn_lastpage.setVisibility(View.VISIBLE);
                                btn_nextpage.setVisibility(View.VISIBLE);
                            }
                            if (Page_num == Total_page) {
                                btn_lastpage.setVisibility(View.VISIBLE);
                                btn_nextpage.setVisibility(View.INVISIBLE);
                            }

                            getList();
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MainActivity.this, "输入大于总页面数", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    }

                }

                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                //使EditText触发一次失去焦点事件
                v.setFocusable(false);
//                v.setFocusable(true); //这里不需要是因为下面一句代码会同时实现这个功能
                v.setFocusableInTouchMode(true);
                return true;
            }
        }
        return false;
    }
}
