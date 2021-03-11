package uz.android.dictionary

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.add_word_dialog.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import kotlinx.android.synthetic.main.item_words.*
import uz.android.dictionary.adapters.RecyclerAdapter
import uz.android.dictionary.databinding.ActivityMainBinding
import uz.android.dictionary.models.RoomWords
import uz.android.dictionary.room.RoomDbHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecyclerAdapter
    private lateinit var words: RoomWords
    private lateinit var wortdLists: ArrayList<RoomWords>
    private  lateinit var dbHelper: RoomDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = RoomDbHelper.DatabaseBuilder.getInstance(this)
        setAdapter(dbHelper.roomDao().getAllWords())


        binding.add.setOnClickListener {

            val dialog = AlertDialog.Builder(this).create()
            val dialogView = LayoutInflater.from(this).inflate(R.layout.add_word_dialog, null, false)

            val saqla = dialogView.findViewById<Button>(R.id.save)
            val yop = dialogView.findViewById<Button>(R.id.cancel)
            val eng = dialogView.findViewById<EditText>(R.id.wordeng)
            val uz = dialogView.findViewById<EditText>(R.id.wordeuz)
            val ru = dialogView.findViewById<EditText>(R.id.wordru)

            yop.setOnClickListener {
                dialog.cancel()
            }
            saqla.setOnClickListener {

                if(eng.text.toString().isNotEmpty() && uz.text.toString().isNotEmpty() && ru.text.toString().isNotEmpty()) {
                        words = RoomWords(
                                wordeng = eng.text.toString(),
                                worduz = uz.text.toString(),
                                wordru = ru.text.toString())
                        dbHelper.roomDao().inset(words)
                        Toast.makeText(this, "Word added", Toast.LENGTH_SHORT).show()
                        setAdapter(dbHelper.roomDao().getAllWords())
                        dialog.cancel()

                }
                else{
                    Toast.makeText(this, "Iltimos hamma maydonlarni to'ldiring", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.setView(dialogView)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
        }
    }

    private fun showBottomSheetDialog(words: RoomWords) {
        val dbHelper: RoomDbHelper = RoomDbHelper.DatabaseBuilder.getInstance(this)
        val dialog = BottomSheetDialog(this)
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null, false)
        dialogView.maqoleng.text = words.wordeng
        dialogView.maqoluz.text = words.worduz
        dialogView.maqolru.text = words.wordru
        val ochir = dialogView.findViewById<FloatingActionButton>(R.id.delete)
        val jonat = dialogView.findViewById<FloatingActionButton>(R.id.share)
        ochir.setOnClickListener {

            dbHelper.roomDao().delete(words)
            setAdapter(dbHelper.roomDao().getAllWords())
            Toast.makeText(this, "Word deleted", Toast.LENGTH_SHORT).show()
            dialog.cancel()
        }
        jonat.setOnClickListener {
            val intent= Intent()
            intent.action= Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,words.wordeng)
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share To:"))
        }
        dialog.setContentView(dialogView)
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.sord_asc ->{

                val sortedBy  = dbHelper.roomDao().getAllWords().sortedBy {
                    it.wordeng
                }
                setAdapter(sortedBy)
            }
        }

        return true
    }
    private fun setAdapter(list: List<RoomWords>) {
        adapter = RecyclerAdapter(list){ it, position ->
            showBottomSheetDialog(it)
        }
        binding.rv.adapter = adapter

        if(list.isNotEmpty())
        {
            binding.text.visibility = View.GONE
        }

    }
}