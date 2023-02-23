package br.com.fiap.app_investment_consulting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.card_product.view.*

class ProductsActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var modelProducts = productModelList()


    private fun productModelList():List<ProductModel>{
        var productsList = ArrayList<ProductModel>()
        productsList.add(ProductModel(R.drawable.acoes,"Tesouro direto","Moderado","100","1.3","2.5"))
        productsList.add(ProductModel(R.drawable.fixa,"CDB","Auto","500","2.5","4.3"))
        productsList.add(ProductModel(R.drawable.investidores,"CDI","Auto","1000","3.5","8"))
        return productsList
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        var adapter = ProductsAdapter(modelProducts)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.onItemClick= { product ->
            if (txtStartValue.text.toString().isEmpty()){
                Toast.makeText(
                    this,
                    R.string.txtInfo,
                    Toast.LENGTH_LONG).show()
            }else{
                var result = Intent(this, ResultActivity::class.java)
                //start Value

                result.putExtra("starValue",txtStartValue.text.toString())
                //product
                result.putExtra("product",product.name)
                result.putExtra("risk",product.risk)
                result.putExtra("fee",product.fee)
                result.putExtra("monthly",product.monthly)
                result.putExtra("yield",product.yield)
                //client
                result.putExtra("name",intent.getStringExtra("name"))
                result.putExtra("email",intent.getStringExtra("email"))
                result.putExtra("phone",intent.getStringExtra("phone"))
                startActivity(result)
            }
        }
        val name = intent.getStringExtra("name")
        lbSetProduct.text = "Olá, ${name}. ${lbSetProduct.text}."
    }

}

//class Products :: RecyclerView :: set value in CardView and open new activity
class ProductsAdapter(dataProducts:List<ProductModel>) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    var onItemClick: ((ProductModel) -> Unit)? = null
    var dataSet: List<ProductModel> = dataProducts
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_product, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.itemImage.setImageResource(dataSet[position].img)
        viewHolder.itemName.text = dataSet[position].name
        viewHolder.itemRisk.text = dataSet[position].risk
        viewHolder.itemFee.text = dataSet[position].fee
        viewHolder.itemMonthLy.text = dataSet[position].monthly
        viewHolder.itemPosition = position
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemImage: ImageView
        var itemName: TextView
        var itemRisk: TextView
        var itemFee: TextView
        var itemMonthLy: TextView
        var itemPosition: Int = 0
        init {
            itemImage = itemView.findViewById(R.id.ImgProd)
            itemName = itemView.findViewById(R.id.txtName)
            itemRisk = itemView.findViewById(R.id.txtRisk)
            itemMonthLy = itemView.findViewById(R.id.txtMonthLy)
            itemFee = itemView.findViewById(R.id.txtFee)
            itemView.faAdd.setOnClickListener {
                onItemClick?.invoke(dataSet[itemPosition])
            }
        }
    }
}

