package com.example.bai1

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bai1.databinding.ActivityMainBinding
import kotlin.math.floor
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val mylist : MutableList<Int> = mutableListOf()
    private lateinit var myAdapter: ArrayAdapter<Int>
    private var modeChosen : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initProperty()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initProperty(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupAdaptor() // Thiết lập adapter cho ListView
        setupRadioGroupListener()// Setup listener cho radio group
        setupButtonListener()//Setup listener cho result button
    }



    private fun updateMyListView (){
        val inputValue = binding.inputBar.text.toString().toIntOrNull();
        if(inputValue==null){       //nếu dữ liệu nhập vào lỗi -> báo lỗi
            binding.resultNotification.text = "loi"
        }else{                       //nếu dữ liệu nhập vào k lỗi

            getResultNewList(inputValue) //update lại list mới
            if(mylist.isEmpty()){        //nếu list trống = không có giá trị khả thi
                binding.resultNotification.text = "khong ton tai so kha thi"
            }else{
                binding.resultNotification.text = "Ket qua o bang duoi"
                myAdapter.notifyDataSetChanged()
            }
        }

    }




    private fun getResultNewList(n:Int){
        mylist.clear() // Xóa danh sách cũ
        when(modeChosen){
            1 -> {  // số chẵn
                addAllEvenNumber(n, mylist)
            }

            2 -> {  // số lẻ
                addAllOddNumber(n,mylist)
            }

            3 -> {  //số chính phương
                addPerfectSquares(n,mylist)
            }
        }

    }

    /*--- Thêm số chẵn vào danh sách ---*/
    private fun addAllEvenNumber(untilNum: Int, toAddList: MutableList<Int>) {
        for (number in 0 until untilNum) {
            if (number % 2 == 0) {
            toAddList.add(number)
            }
        }
    }


    /*--- Thêm số lẻ vào danh sách ---*/
    private fun addAllOddNumber(untilNum: Int, toAddList: MutableList<Int>){
        for (number in 0 until untilNum) {
            if (number % 2 == 1) {
                toAddList.add(number)
            }
        }
    }

    private fun addPerfectSquares(n: Int, toAddList: MutableList<Int>) {
        for (number in 0 until n) {
            // Tính căn bậc hai của số
            val sq = sqrt(number.toDouble())

            // Lấy phần nguyên gần nhất của căn bậc hai
            val f = floor(sq)

            if(sq == f){
                toAddList.add(number)
            }
        }
    }

    private fun setupAdaptor(){
        myAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mylist
        )
        binding.resultListView.adapter = myAdapter
    }

    private fun setupButtonListener() {
        binding.resultButton.setOnClickListener {
            updateMyListView() // Gọi hàm Do() khi nút được nhấn
        }
    }

    /*--- Thiết lập listener cho RadioGroup để thay đổi modeChosen ---*/
    private fun setupRadioGroupListener() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId -> modeChosen = when (checkedId) {
            binding.button1.id -> 1
            binding.button2.id -> 2
            binding.button3.id -> 3
            else -> 0
        }
    }
}
}
