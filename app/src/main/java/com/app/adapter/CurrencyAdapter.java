package com.app.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.R;
import com.app.databinding.ItemCurrencyConvertBinding;
import com.app.models.CurrencyModel;
import com.app.models.QtySubmit;
import com.app.viewmodels.ItemCurrencyViewModel;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyView> {
    private List<CurrencyModel> currencies;
    private LayoutInflater layoutInflater;
    public String base;
    private Double qty;
    public MutableLiveData<Object> mMutableLiveData = new MutableLiveData<>();

    public CurrencyAdapter(List<CurrencyModel> currencies,String base,Double qty) {
        this.currencies = currencies;
        this.base = base;
        this.qty = qty;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public CurrencyView onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemCurrencyConvertBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_currency_convert, parent, false);
        return new CurrencyView(binding);
    }


    private static final String TAG = "CurrencyAdapter";
    @Override
    public void onBindViewHolder(@NonNull CurrencyView holder, final int position) {
            ItemCurrencyViewModel itemCurrencyViewModel = new ItemCurrencyViewModel(currencies.get(position), position, qty);
            holder.ItemCurrencyConvertBinding.setItemCurrencyViewModel(itemCurrencyViewModel);
            setEvent(itemCurrencyViewModel);
    }

    private void setEvent(ItemCurrencyViewModel itemCurrencyViewModel) {
        itemCurrencyViewModel.mMutableLiveData.observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object aVoid) {
                mMutableLiveData.setValue(aVoid);
            }
        });
    }


    public List<CurrencyModel> getCurrencies() {
        return currencies;
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public void setData(List<CurrencyModel> currencies, Double qty) {
        this.currencies = currencies;
        this.qty = qty;
        notifyItemRangeChanged(1,currencies.size());
    }

    public void swap(int pos) {
        Collections.swap(currencies, 0, pos);
        notifyItemMoved(0, pos);
    }

    public class CurrencyView extends RecyclerView.ViewHolder {

        private ItemCurrencyConvertBinding ItemCurrencyConvertBinding;

        public CurrencyView(@NonNull ItemCurrencyConvertBinding ItemCurrencyConvertBinding) {
            super(ItemCurrencyConvertBinding.getRoot());
            this.ItemCurrencyConvertBinding = ItemCurrencyConvertBinding;
        }
    }
}
