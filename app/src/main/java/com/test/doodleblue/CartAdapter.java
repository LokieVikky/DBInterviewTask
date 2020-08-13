package com.test.doodleblue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.test.doodleblue.MenuModel.TYPE_FOOTER;
import static com.test.doodleblue.MenuModel.TYPE_HEADER;
import static com.test.doodleblue.MenuModel.TYPE_ITEM;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MenuModel> menuModelList;
    ItemClickListener listener;
    ShowMoreClickListener showMoreClickListener;

    public CartAdapter(List<MenuModel> menuModelList, ItemClickListener listener, ShowMoreClickListener showMoreClickListener) {
        this.listener = listener;
        this.menuModelList = menuModelList;
        this.showMoreClickListener = showMoreClickListener;
    }

    public void setData(List<MenuModel> menuModelList) {
        this.menuModelList = menuModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM: {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
                return new MenuItemHolder(v);
            }
            case TYPE_FOOTER:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_footer, parent, false);
                return new CartFooterHolder(v);
            default:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
                return new MenuItemHolder(v1);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MenuModel model = menuModelList.get(position);
        switch (holder.getItemViewType()) {
            case TYPE_ITEM:
                loadItemData((MenuItemHolder) holder, model);
                break;
            case TYPE_HEADER:
                break;
        }
    }

    public void loadItemData(MenuItemHolder holder, MenuModel model) {
        holder.txtItemName.setText(model.getItemName());
        holder.txtItemDesc.setText(model.getItemDescription());
        holder.txtItemPrice.setText("€"+model.getItemPrice());
        holder.quantity.setCount(model.getItemQuantity());
    }

    @Override
    public int getItemViewType(int position) {
        return menuModelList.get(position).getUIType();
    }

    @Override
    public int getItemCount() {
        return menuModelList.size();
    }

    public class MenuItemHolder extends RecyclerView.ViewHolder {
        TextView txtItemName, txtItemDesc, txtItemPrice;
        IncDecView quantity;

        public MenuItemHolder(@NonNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemDesc = itemView.findViewById(R.id.txtItemDesc);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            quantity = itemView.findViewById(R.id.Qty);
            quantity.setOnValueChangedListener(new IncDecView.OnValueChangedListener() {
                @Override
                public void OnValueChanged(int value) {
                    int currentPos = getAdapterPosition();
//                    // Adding 1 coz there is heading in the reused list
//                    currentPos++;
                    MenuModel model = menuModelList.get(currentPos);
                    model.setItemQuantity(value);
                    menuModelList.set(currentPos, model);
                    listener.OnItemClick(model.getId(), value);
                }
            });
        }
    }

    public class CartFooterHolder extends RecyclerView.ViewHolder {
        TextView txtFooter;

        public CartFooterHolder(@NonNull View itemView) {
            super(itemView);
            txtFooter = itemView.findViewById(R.id.txtFooter);
            txtFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMoreClickListener.OnShowMoreClicked();
                }
            });
        }
    }

    interface ShowMoreClickListener {
        void OnShowMoreClicked();
    }

}
