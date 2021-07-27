package br.com.myapplication.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import br.com.myapplication.R;
import br.com.myapplication.databinding.FragmentProdutoDetalheBinding;
import br.com.myapplication.model.Enterprise;

public class ProdutoDetalheFragment extends Fragment implements View.OnClickListener {

    private FragmentProdutoDetalheBinding produtoDetalheBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.produtoDetalheBinding = FragmentProdutoDetalheBinding.inflate(inflater, container, false);
        this.produtoDetalheBinding.setLifecycleOwner(this);

        ((AppCompatActivity) getActivity()).setSupportActionBar(this.produtoDetalheBinding.toolbar);
        setHasOptionsMenu(true);
        setDetalhe();
        this.produtoDetalheBinding.toolbar.setNavigationOnClickListener(this);
        return this.produtoDetalheBinding.getRoot();
    }

    @SuppressLint("CheckResult")
    private void setDetalhe() {

        assert getArguments() != null;
        Enterprise enterprise =   ProdutoDetalheFragmentArgs.fromBundle(getArguments()).getMessageSent();

        getActivity().setTitle(enterprise.getEnterprise_name());
        produtoDetalheBinding.setVariable(BR.produto_item_detalhe, enterprise);
        produtoDetalheBinding.executePendingBindings();
        RequestOptions options = new RequestOptions();
        options.error(R.drawable.img_detalhe);
        Glide.with(this.produtoDetalheBinding.getRoot()).setDefaultRequestOptions(options).load(enterprise.getPhoto()).apply(options).into(this.produtoDetalheBinding.imCompany);
    }


    @Override
    public void onClick(View v) {
        NavHostFragment.findNavController(this).popBackStack();
    }
}