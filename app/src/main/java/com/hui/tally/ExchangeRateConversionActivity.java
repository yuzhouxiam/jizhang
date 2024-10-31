package com.hui.tally;

import android.annotation.SuppressLint;
import java.math.BigDecimal;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExchangeRateConversionActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, LoadDataAsyncTask.onGetNetDataListener {

    private final static String EXCHANGE_RATE_CONVERSION_API = "https://open.er-api.com/v6/latest/";
    // 定义下拉列表需要显示的单位名称
    private static final String[] nameArray = {
            "人民币", "美元", "欧元", "日元", "港币", "韩元",
            "英镑", "泰铢", "新台币", "越南盾", "阿尔及利亚第纳尔", "阿根廷比索",
            "阿联酋迪拉姆", "阿曼里亚尔", "澳大利亚元", "澳门元", "白俄罗斯卢布", "巴林第纳尔",
            "保加利亚新列弗", "巴西雷亚尔", "冰岛克朗", "波兰兹罗提", "丹麦克朗", "俄罗斯卢布",
            "菲律宾比索", "哥伦比亚比索", "哥斯达黎加科朗", "加拿大元", "柬埔寨瑞尔", "埃及镑",
            "捷克克朗", "卡塔尔里亚尔", "克罗地亚库纳", "肯尼亚先令", "科威特第纳尔", "老挝基普",
            "离岸人民币", "黎巴嫩镑", "罗马尼亚列伊", "马来西亚林吉特", "孟加拉塔卡", "缅甸元",
            "摩洛哥迪拉姆", "墨西哥比索", "南非兰特", "挪威克朗", "瑞典克朗", "瑞士法郎",
            "塞尔维亚第纳尔", "沙特里亚尔", "斯里兰卡卢比", "坦桑尼亚先令", "土耳其里拉", "文莱元",
            "乌干达先令", "乌克兰格里夫纳", "新加坡元", "新西兰元", "匈牙利福林", "叙利亚镑",
            "伊拉克第纳尔", "印度卢比", "印度尼西亚盾", "以色列新谢克尔", "约旦第纳尔", "赞比亚克瓦查",
            "智利比索"
    };
    // 定义下拉列表需要显示的单位数组
    private static final String[] unitArray = {
            "CNY", "USD", "EUR", "JPY", "HKD", "KRW",
            "GBP", "THB", "TWD", "VND", "DZD", "ARS",
            "AED", "OMR", "AUD", "MOP", "BYN", "BHD",
            "BGN", "BRL", "ISK", "PLN", "DKK", "RUB",
            "PHP", "COP", "CRC", "CAD", "KHR", "EGP",
            "CZK", "QAR", "HRK", "KES", "KWD", "LAK",
            "CNH", "LBP", "RON", "MYR", "BDT", "MMK",
            "MAD", "MXN", "ZAR", "NOK", "SEK", "CHF",
            "RSD", "SAR", "LKR", "TZS", "TRY", "BND",
            "UGX", "UAH", "SGD", "NZD", "HUF", "SYP",
            "IQD", "INR", "IDR", "ILS", "JOD", "ZMW",
            "CLP"
    };
    private Spinner sp_select1;
    private Spinner sp_select2;
    TextView tv_value1;
    TextView tv_unit1;
    TextView tv_value2;
    TextView tv_unit2;
    private List<Button> buttonList = new ArrayList<>();
    ImageButton iv_del;

    // 单位一
    private String unit1 = "人民币";
    // 单位二
    private String unit2 = "美元";
    // 数值一
    private String value1 = "0";
    // 数值二
    private String value2 = "0";
    private Map<String, Object> map;
    private Map<String, Double> map1;
    private int index = 1;
    private double exchange_rate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_conversion);

        // 获取控件，并添加点击事件
        findViewById(R.id.iv_back).setOnClickListener(this);

        sp_select1 = findViewById(R.id.sp_select1);
        sp_select2 = findViewById(R.id.sp_select2);
        tv_value1 = findViewById(R.id.tv_value1);
        tv_unit1 = findViewById(R.id.tv_unit1);
        tv_value2 = findViewById(R.id.tv_value2);
        tv_unit2 = findViewById(R.id.tv_unit2);

        buttonList.add(findViewById(R.id.btn_0));
        buttonList.add(findViewById(R.id.btn_1));
        buttonList.add(findViewById(R.id.btn_2));
        buttonList.add(findViewById(R.id.btn_3));
        buttonList.add(findViewById(R.id.btn_4));
        buttonList.add(findViewById(R.id.btn_5));
        buttonList.add(findViewById(R.id.btn_6));
        buttonList.add(findViewById(R.id.btn_7));
        buttonList.add(findViewById(R.id.btn_8));
        buttonList.add(findViewById(R.id.btn_9));
        buttonList.add(findViewById(R.id.btn_pt));
        buttonList.add(findViewById(R.id.btn_clr));
        iv_del = findViewById(R.id.iv_del);

        // 给按钮设置的点击事件
        for (Button button : buttonList) {
            button.setOnClickListener(this);
        }
        iv_del.setOnClickListener(this);

        // 声明一个映射对象的列表，用于保存名称与单位配对信息
        List<Map<String, Object>> list = new ArrayList<>();
        // name是名称，unit是单位
        for (int i = 0; i < nameArray.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", nameArray[i]);
            item.put("unit", unitArray[i]);
            list.add(item);
        }

        // 声明一个下拉列表的简易适配器，其中指定了名称与单位两组数据
        SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.item_value_conversion,
                new String[]{"name", "unit"},
                new int[]{R.id.tv_name, R.id.tv_unit});
        sp_select1.setAdapter(adapter);
        sp_select2.setAdapter(adapter);
        // 设置下拉列表默认显示
        sp_select1.setSelection(0);
        sp_select2.setSelection(1);
        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的 onItemSelected 方法
        sp_select1.setOnItemSelectedListener(this);
        sp_select2.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.iv_back && value1.equals("暂未找到该货币的汇率"))
            return;
        if (v.getId() != R.id.iv_back && v.getId() != R.id.iv_del && v.getId() != R.id.btn_clr && value1.length() >= 20)
            return;
        String inputText = "";
        // 如果不是删除按钮和返回按钮
        if (v.getId() != R.id.iv_del && v.getId() != R.id.iv_back) {
            inputText = ((TextView) v).getText().toString();
        }

        if(v.getId()==R.id.iv_back) {
            // 点击了返回按钮
            finish();
        }
            // 点击了清除按钮
        else if(v.getId()== R.id.btn_clr) {
            clear();
        }

            // 点击了删除按钮
        else if(v.getId()== R.id.iv_del) {
            delete();
        }

            // 点击了小数点按钮
        else if(v.getId()== R.id.btn_pt){
                if (value1.indexOf(".") == -1)
                    value1 = value1 + ".";
                refreshText();
        }

            // 点击了数字按钮
            else{
                if (value1.equals("0")) {
                    value1 = inputText;
                } else {
                    value1 = value1 + inputText;
                }
                operation();
                refreshText();
        }
    }

    // 清空并初始化
    private void clear() {
        value1 = "0";
        if (!value2.equals("暂未找到该货币的汇率"))
            value2 = "0";
        refreshText();
    }

    // 刷新文本显示
    private void refreshText() {
        tv_value1.setText(value1);
        tv_value2.setText(value2);
    }

    // 回退
    private void delete() {
        if (value1.length() != 0) {
            value1 = value1.substring(0, value1.length() - 1);
            if (value1.length() == 0)
                value1 = "0";
            operation();
            refreshText();
        }
    }

    // 运算
    private void operation() {
        if (value1.equals("暂未找到该货币的汇率") || value2.equals("暂未找到该货币的汇率"))
            return;
        if (unit1.equals(unit2))
            value2 = value1;
        else if (Double.parseDouble(value1) == 0)
            value2 = "0";
        else {
            BigDecimal db1 = new BigDecimal(value1);
            BigDecimal db2 = new BigDecimal(String.valueOf(exchange_rate));
            value2 = String.valueOf(db1.multiply(db2).doubleValue());
        }
    }

    // 选中某一项之后触发的回调方法
    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tv_unit = adapterView.findViewById(R.id.tv_unit);
        tv_unit.setVisibility(GridView.GONE);

        if (adapterView.getId() == R.id.sp_select1) {
            tv_unit1.setText(unitArray[i]);
            unit1 = nameArray[i];
            // 调用接口api，获取该货币的汇率
            LoadDataAsyncTask task = new LoadDataAsyncTask(this, this, false);
            task.execute(EXCHANGE_RATE_CONVERSION_API + unitArray[i]);
            operation();
            refreshText();
        } else {
            tv_unit2.setText(unitArray[i]);
            unit2 = nameArray[i];
            index = i;
            if (map1 != null) {
                try {
                    exchange_rate = map1.get(unitArray[i]);
                    value2 = "0";
                    operation();
                } catch (Exception e) {
                    e.printStackTrace();
                    value2 = "暂未找到该货币的汇率";
                }
            }
            refreshText();
        }
    }

    // 什么也没选执行的回调方法
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // 成功调用网络接口，处理返回结果
    @Override
    public void onSucess(String json) {
        map = new Gson().fromJson(json, Map.class);
        if (map != null) {
            if (map.get("result").equals("success")) {
                if (value1.equals("暂未找到该货币的汇率") || value2.equals("暂未找到该货币的汇率"))
                    value1 = "0";
                map1 = (Map<String, Double>) map.get("rates");
                try {
                    exchange_rate = map1.get(unitArray[index]);
                    value2 = "0";
                    operation();
                } catch (Exception e) {
                    e.printStackTrace();
                    value2 = "暂未找到该货币的汇率";
                }
                refreshText();
                return;
            }
        }
        map1 = null;
        value1 = "暂未找到该货币的汇率";
        refreshText();
    }

}
