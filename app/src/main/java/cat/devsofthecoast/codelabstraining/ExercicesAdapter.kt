package cat.devsofthecoast.codelabstraining

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExercicesAdapter(private val listener: Listener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data: List<String> = listOf(
        "Exercice 1 - Notifications",
        "Exercice 2 - WorkManager (Java)",
        "Exercice 3 - WorkManager (Kotlin)",
        "Exercice 4 - ????",
        "Exercice 5 - ????",
        "Exercice 6 - ????",
        "Exercice 7 - ????",
        "Exercice 8 - ????",
        "Exercice 9 - ????",
        "Exercice 9 - ????",
        "Exercice 9 - ????",
        "Exercice 9 - ????",
        "Exercice 9 - ????",
        "Exercice 9 - ????",
        "Exercice 9 - ????",
        "Exercice 9 - ????",
        "Exercice 9 - ????",
        "Exercice 9 - ????",
        "Exercice 9 - ????",
        "Exercice 9 - ????",
        "Exercice 9 - ????",
        "Exercice 10 - ????"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_exericice, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = ALL_ITEMS_SAME_VIEWTYPE

    private class ViewHolder(
        itemView: View,
        val listener: Listener
    ) : RecyclerView.ViewHolder(itemView) {

        val tvExerciceName: TextView = itemView.findViewById(R.id.tvExerciceName)

        init {
            itemView.setOnClickListener {
                listener.onItemClicked(tvExerciceName.text.toString(), adapterPosition)
            }
        }

        fun bind(exerciceName: String) {
            tvExerciceName.text = exerciceName
        }
    }

    public interface Listener {
        fun onItemClicked(exerciceName: String, position: Int)
    }

    companion object {
        const val ALL_ITEMS_SAME_VIEWTYPE: Int = 1
    }
}
