package br.com.myapplication.view;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

import br.com.myapplication.BR;
import br.com.myapplication.R;
import br.com.myapplication.databinding.FragmentProdutoListBinding;
import br.com.myapplication.model.Enterprise;
import br.com.myapplication.view.adapter.ProdutoAdapter;
import br.com.myapplication.view.adapter.listener.OnItemClickListener;
import br.com.myapplication.viewmodel.ProdutoListViewModel;

import static br.com.myapplication.constants.constant.KEY_DICE;

public class ProdutoListFragment extends Fragment {

    private FragmentProdutoListBinding produtoListBinding;
    private ProdutoListViewModel listViewModel;
    private ProdutoAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.produtoListBinding = FragmentProdutoListBinding.inflate(inflater, container, false);
        this.produtoListBinding.setLifecycleOwner(this);

        this.adapter = new ProdutoAdapter();
        this.produtoListBinding.setMyAdapter(this.adapter);
        this.listViewModel = new ViewModelProvider(this).get(ProdutoListViewModel.class);
        this.listViewModel.Catalog();

        loadObservers();
        onItemClick();
        ((AppCompatActivity) getActivity()).setSupportActionBar(this.produtoListBinding.toolbar);
        getActivity().setTitle("");
        setHasOptionsMenu(true);

        return this.produtoListBinding.getRoot();
    }

    private void onItemClick() {

        this.adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int posicao) {

                ProdutoListFragmentDirections.ActionProdutoListFragmentToProdutodetailsFragment nav =
                        ProdutoListFragmentDirections.actionProdutoListFragmentToProdutodetailsFragment((Enterprise) object);
                NavHostFragment.findNavController(ProdutoListFragment.this).navigate(nav);

            }
        });
    }

    private void loadObservers() {

        this.produtoListBinding.setVariable(BR.setProgressListPROD, true);
        this.listViewModel.getList.observe(getViewLifecycleOwner(), new Observer<List<Enterprise>>() {
            @Override
            public void onChanged(List<Enterprise> enterprises) {
                if (enterprises != null) {
                    adapter.setListForAdpter(enterprises);
                } else {
                    Toast.makeText(getActivity(), "Não foi encontrado esse produto", Toast.LENGTH_SHORT).show();
                }

                produtoListBinding.setVariable(BR.setProgressListPROD, false);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}