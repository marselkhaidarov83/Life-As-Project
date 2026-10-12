package com.MH83.LifeAsProject.view.Money.MoneyCapital;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.adapter.rView.money.MoneyLotAssetOperationAdapter;
import com.MH83.LifeAsProject.custom.CustomStaticClass;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumLotAssetOperation;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Money.MoneyAsset;
import com.MH83.LifeAsProject.model.Money.MoneyLotAsset;
import com.MH83.LifeAsProject.model.Money.MoneyLotAssetOperation;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class FragmentMoneyLotAssetPopupNew extends CustomPopupBottomFragment<MoneyLotAsset>
    implements IFragmentViewCustom
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_lot_asset_new;
    //endregion

    //region Fragment parameters
    private final ArrayList<MoneyLotAssetOperation> _deletedMoneyLotAssetOperations = new ArrayList<>();
    //endregion

    //region Fragment settings
    private Calendar _calendar;
    //endregion

    //region Fragment Components
//    private TextView _textViewGroupPortfolio;
    private TextView _textViewPortfolio;
    protected TextView _textViewPortfolio_;
//    private TextView _textViewTypeAsset;
    private TextView _textViewAsset;
    private TextView _textViewCreationDate;
//    private TextView _textViewFinishDate;
    private RecyclerView _recyclerView;
    private ImageView _imageViewAddItem;
    private TableRow _tableRowPortfolio;
    private ConstraintLayout _constraintLayoutHead;
    //endregion

    //region Constructors
    public FragmentMoneyLotAssetPopupNew()
    {
        super(_layoutId,
                R.string.PopupLotAssetAdd,
                R.string.PopupLotAssetUpdate,
                R.string.errorUpdateMoneyLotAsset);
    }

    public FragmentMoneyLotAssetPopupNew(AppCompatActivity activity,
                                         MoneyLotAsset data,
                                         EnumTypeAction typeAction,
                                         IFragmentViewCustom iFragmentViewCustom,
                                         Calendar calendar)
    {
        super(activity, _layoutId, data, typeAction,
                null,
                null,
                R.string.PopupLotAssetAdd,
                R.string.PopupLotAssetUpdate,
                R.string.errorUpdateMoneyLotAsset,
                iFragmentViewCustom,
                null,
                null);

        _calendar = calendar;
    }
    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

//        _textViewGroupPortfolio = view.findViewById(R.id.textViewGroupPortfolio);
        _textViewPortfolio = view.findViewById(R.id.textViewPortfolio);
        _textViewPortfolio_ = view.findViewById(R.id.textViewPortfolio_);
//        _textViewTypeAsset = view.findViewById(R.id.textViewTypeAsset);
        _textViewAsset = view.findViewById(R.id.textViewAsset);
        _textViewCreationDate = view.findViewById(R.id.textViewCreationDate);
//        _textViewFinishDate = view.findViewById(R.id.textViewFinishDate);
        _recyclerView = view.findViewById(R.id.recycleViewOperations);
        _imageViewAddItem = view.findViewById(R.id.imageViewAddItem);
        _tableRowPortfolio = view.findViewById(R.id.tableRowPortfolio);
        _constraintLayoutHead = view.findViewById(R.id.constraintLayoutHead);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = null;
        _focusedViewToUpdate = null;
        _showInputToAdd = showInput;
        _showInputToUpdate = showInput;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getDialog().getWindow().setLayout(
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Lot_Asset_Width),
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        else
        {
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();

            if (globalVariable.get_typeDevice() == EnumTypeDevice.Phone)
            {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels),
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
            else
            {
                getDialog().getWindow().setLayout(
                        (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_Lot_Asset_Width),
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }
    }
    //endregion

    //region Set Components Setting
    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        _imageViewAddItem.setVisibility((_data != null) &&
                !Objects.equals(_data.get_typeMoneyLotAsset(), MoneyLotAsset.EnumTypeMoneyLotAsset.LotCredit) ?
                View.VISIBLE : View.INVISIBLE);

        if (_data.get_moneyAsset().get_wallet_id() != 0)
        {
            _tableRowPortfolio.getLayoutParams().height = 0;
            _textViewPortfolio.getLayoutParams().height = 0;
            _textViewPortfolio_.getLayoutParams().height = 0;
            _constraintLayoutHead.getLayoutParams().height =
                    (int) getContext().getResources().getDimension(R.dimen.MoneyPopup_LotAsset_Height1);
        }
    }
    //endregion

    //region Set Adapters
    @Override
    protected void SetAdapters()
    {
        super.SetAdapters();

        FillRecyclerView();
    }

    private void FillRecyclerView()
    {
        MoneyLotAssetOperationAdapter adapter = new MoneyLotAssetOperationAdapter(
                getContext(), _data.get_lotAssetOperations(), _typeAction, _iFragmentViewCustom, this);
        _recyclerView.setAdapter(adapter);
    }
    //endregion

    //region Set Listeners
    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _imageViewAddItem.setOnClickListener(view ->
        {
            MoneyLotAssetOperation moneyLotAssetOperation = new MoneyLotAssetOperation(
                    0L,
                    _data,
                    false,
                    EnumLotAssetOperation.Revaluation,
                    0L,
                    0L,
                    _data.get_count(),
                    _data.get_count(),
                    0L,
                    0L,
                    0L,
                    0L,
                    0L,
                    _calendar);

            new FragmentMoneyLotAssetOperationPopupNew(
                    (AppCompatActivity) getContext(),
                    moneyLotAssetOperation,
                    _typeAction,
                    EnumTypeAction.Insert,
                    _iFragmentViewCustom,
                    this,
                    false,
                    null).PopupShow();
        });

        _textViewCaption.setOnClickListener(view -> _recyclerView.scrollToPosition(0));
    }
    //endregion

    //region Set Params
    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(null);
    }

    protected void SetValues(Object[] objects)
    {
        if (_data != null)
        {
//            set_GroupPortfolio(_data.get_groupPortfolioName());
            set_Portfolio(_data.get_groupPortfolioName(), _data.get_portfolioName());
//            set_TypeAsset(_data.get_moneyAsset().get_moneyTypeAssetName());
            set_Asset(_data.get_moneyAsset().get_moneyTypeAssetName(), _data.get_moneyAsset().get_name());
//            set_CreationDate(_data.get_calendarStart());
//            set_FinishDate(_data.get_calendarEnd());
            set_Dates(_data.get_calendarStart(), _data.get_calendarEnd());
        }
        else
        {
//            set_GroupPortfolio("");
            set_Portfolio("", "");
//            set_TypeAsset("");
            set_Asset("", "");
//            set_CreationDate(Calendar.getInstance());
//            set_FinishDate(Calendar.getInstance());
            set_Dates(Calendar.getInstance(), Calendar.getInstance());
        }
    }

//    private void set_GroupPortfolio(String groupPortfolioName)
//    {
//        _textViewGroupPortfolio.setText(groupPortfolioName);
//    }
    @SuppressLint("SetTextI18n")
    private void set_Portfolio(String groupPortfolioName, String portfolioName)
    {
        _textViewPortfolio.setText(groupPortfolioName + " / " + portfolioName);
    }
//    private void set_TypeAsset(String typeAssetName)
//    {
//        _textViewTypeAsset.setText(typeAssetName);
//    }
    @SuppressLint("SetTextI18n")
    private void set_Asset(String typeAssetName, String assetName)
    {
        _textViewAsset.setText(typeAssetName + " / " + assetName);
    }
//    private void set_CreationDate(Calendar calendar)
//    {
//        CustomStaticClass.setDateView(getContext(),
//                calendar.get(Calendar.DATE),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.YEAR),
//                _textViewCreationDate);
//    }
//    private void set_FinishDate(Calendar calendar)
//    {
//        if (calendar.get(Calendar.YEAR) == 2900)
//        {
//            _textViewFinishDate.setText("");
//            return;
//        }
//
//        CustomStaticClass.setDateView(getContext(),
//                calendar.get(Calendar.DATE),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.YEAR),
//                _textViewFinishDate);
//    }

    private void set_Dates(Calendar calendar1, Calendar calendar2)
    {
        String s = CustomStaticClass.getDate4View(
                getContext(),
                calendar1.get(Calendar.DATE),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.YEAR),
                true,
                false,
                false) + " / ";

        if (calendar2.get(Calendar.YEAR) != 2900)
            s += CustomStaticClass.getDate4View(
                            getContext(),
                            calendar2.get(Calendar.DATE),
                            calendar2.get(Calendar.MONTH),
                            calendar2.get(Calendar.YEAR),
                            false,
                            false,
                            false);

        _textViewCreationDate.setText(s);
    }
    //endregion

    //region Check Params
    @Override
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
    }

    protected boolean CheckFormValues()
    {
        return true;
    }

    protected boolean CheckFormValuesForDelete() { return true; }
    //endregion

    //region Get Params
    private Long get_Id()
    {
        return _data != null ? _data.get_id() : 0L;
    }
    private Long get_groupPortfolioId()
    {
        return _data == null ? 0L : _data.get_groupPortfolioId();
    }
    private String get_groupPortfolioName()
    {
        return _data == null ? "" : _data.get_groupPortfolioName();
    }
    private Long get_portfolioId()
    {
        return _data == null ? 0L : _data.get_portfolioId();
    }
    private String get_portfolioName()
    {
        return _data == null ? "" : _data.get_portfolioName();
    }
    private Long get_portfolioWallet()
    {
        return _data == null ? 0L : _data.get_portfolioWalletId();
    }
    private MoneyAsset get_MoneyAsset()
    {
        return _data == null ? null : _data.get_moneyAsset();
    }
    private String get_name()
    {
        return _data == null ? "" : _data.get_name();
    }
    private String get_comment()
    {
        return _data == null ? "" : _data.get_comment();
    }
    private Integer get_position()
    {
        return _data == null ? 0 : _data.get_position();
    }
    private Integer get_count()
    {
        return _data == null ? 0 : _data.get_count();
    }
    private Calendar get_calendar_start()
    {
        return _data == null ? Calendar.getInstance() : _data.get_calendarStart();
    }
    private Calendar get_calendar_end()
    {
        return _data == null ? Calendar.getInstance() : _data.get_calendarEnd();
    }
    private EnumStatus get_status()
    {
        return _data == null ? EnumStatus.Started : _data.get_status();
    }
    private ArrayList<MoneyLotAssetOperation> get_MoneyLotAssetOperations()
    {
        return ((MoneyLotAssetOperationAdapter) _recyclerView.getAdapter()).get_dataSet();
    }
    private ArrayList<MoneyLotAssetOperation> get_deletedMoneyLotAssetOperations()
    {
        return _deletedMoneyLotAssetOperations;
    }
    //endregion

    //region Save Params
    protected MoneyLotAsset GetDataFromForm()
    {
        try
        {
            return new MoneyLotAsset(
                    get_Id(),
                    get_groupPortfolioId(),
                    get_groupPortfolioName(),
                    get_portfolioId(),
                    get_portfolioName(),
                    get_portfolioWallet(),
                    get_MoneyAsset(),
                    get_name(),
                    get_comment(),
                    get_position(),
                    get_count(),
                    get_calendar_start(),
                    get_calendar_end(),
                    get_status(),
                    get_MoneyLotAssetOperations(),
                    get_deletedMoneyLotAssetOperations());
        }
        catch (Exception e)
        {
            return  null;
        }
    }
    //endregion

    //region IFragmentViewCustom
    @Override
    public boolean refreshViewOnAdd(Object object)
    {
        if (object instanceof MoneyLotAssetOperation)
        {
            ((MoneyLotAssetOperationAdapter) _recyclerView.getAdapter()).AddItem((MoneyLotAssetOperation) object);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnUpdate(Object object)
    {
        if (object instanceof MoneyLotAssetOperation)
        {
            ((MoneyLotAssetOperationAdapter) _recyclerView.getAdapter()).UpdateItem((MoneyLotAssetOperation) object);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnDelete(Object object)
    {
        if (object instanceof MoneyLotAssetOperation)
        {
            MoneyLotAssetOperation moneyLotAssetOperation = (MoneyLotAssetOperation) object;
            if (moneyLotAssetOperation.get_id() != 0L)
                _deletedMoneyLotAssetOperations.add(moneyLotAssetOperation);
            ((MoneyLotAssetOperationAdapter) _recyclerView.getAdapter()).DeleteFromDataset(moneyLotAssetOperation);

            return true;
        }

        return false;
    }

    @Override
    public boolean refreshViewOnSelect(Object object) {
        return false;
    }
    //endregion
}