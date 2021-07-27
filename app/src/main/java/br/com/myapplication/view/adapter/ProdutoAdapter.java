package br.com.myapplication.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.myapplication.BR;
import br.com.myapplication.R;
import br.com.myapplication.databinding.ProdutoListItemBinding;
import br.com.myapplication.model.Enterprise;
import br.com.myapplication.view.adapter.listener.OnItemClickListener;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> implements Filterable {

    private List<Enterprise> produtoData;
    private List<Enterprise> filterProdutoData;
    private ProdutoListItemBinding itemBinding;
    private OnItemClickListener onItemClickListener;
    private FilterValue valueFilter;

    public ProdutoAdapter() {
        this.produtoData = new ArrayList<>();
        this.filterProdutoData = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setListForAdpter(List<Enterprise> listProd) {
        notifyItemRangeRemoved(0, this.produtoData.size());
        this.produtoData.clear();
        this.produtoData.addAll(listProd);
        this.filterProdutoData.clear();
        this.filterProdutoData.addAll(listProd);
        notifyItemRangeInserted(0, this.produtoData.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        this.itemBinding = ProdutoListItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        this.itemBinding.setLifecycleOwner(this.itemBinding.getLifecycleOwner());
        return new ViewHolder(this.itemBinding);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Enterprise produtoData = this.produtoData.get(i);
        viewHolder.bind(produtoData);
        viewHolder.enterprise = produtoData;

        RequestOptions options = new RequestOptions();
        options.error(R.drawable.img_detalhe);
        Glide.with(this.itemBinding.getRoot()).setDefaultRequestOptions(options).load(produtoData.getPhoto()).apply(options).into(this.itemBinding.imCompany);
    }

    @Override
    public int getItemCount() {
        return this.produtoData.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new FilterValue();
        }
        return valueFilter;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements LifecycleOwner, View.OnClickListener {

        private ProdutoListItemBinding mCaralog;
        private Enterprise enterprise;

        public ViewHolder(ProdutoListItemBinding item) {
            super(item.getRoot());
            this.mCaralog = item;
            itemView.setOnClickListener(this);
        }

        public void bind(Enterprise model) {
            this.mCaralog.setVariable(BR.produto_item_list, model);
            this.mCaralog.executePendingBindings();
        }

        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return LifecycleRegistry.createUnsafe(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(this.enterprise, getAdapterPosition());
        }
    }

    class FilterValue extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<Enterprise> filterList = new ArrayList<>();

                for (int i = 0; i < filterProdutoData.size(); i++) {
                    if ((filterProdutoData.get(i).getEnterprise_name().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(filterProdutoData.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = filterProdutoData.size();
                results.values = filterProdutoData;
            }
            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            produtoData = (List<Enterprise>) results.values;
            notifyDataSetChanged();
        }
    }
}
