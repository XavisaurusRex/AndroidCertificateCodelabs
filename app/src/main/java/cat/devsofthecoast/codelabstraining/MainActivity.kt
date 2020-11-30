package cat.devsofthecoast.codelabstraining

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.devsofthecoast.codelabstraining.exercice01.Exercice1Activity
import cat.devsofthecoast.codelabstraining.exercice02.Exercice2Activity
import cat.devsofthecoast.codelabstraining.exercice03.Exercice3Activity

class MainActivity : AppCompatActivity(), ExercicesAdapter.Listener {

    val rcyCodelabs: RecyclerView by lazy {
        findViewById(R.id.rcyCodelabs);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureRecycler(this);
    }

    private fun configureRecycler(context: Context) {
        rcyCodelabs.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = ExercicesAdapter(this@MainActivity)
        }
    }

    override fun onItemClicked(exerciceName: String, position: Int) {
        var intent: Intent? = null
        when (position) {
            0 -> intent = Intent(this, Exercice1Activity::class.java)

            1 -> intent = Intent(this, Exercice2Activity::class.java)

            2 -> intent = Intent(this, Exercice3Activity::class.java)
            else -> Toast.makeText(
                this,
                "$exerciceName - Not implemented yet!",
                Toast.LENGTH_SHORT
            ).show()
        }
        startActivity(intent);
    }
}