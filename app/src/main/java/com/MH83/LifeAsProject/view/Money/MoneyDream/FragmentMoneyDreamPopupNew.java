package com.MH83.LifeAsProject.view.Money.MoneyDream;

import static com.MH83.LifeAsProject.interfac.Constants.REQUEST_GALLERY_FOR_MONEY_DREAM_POPUP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MH83.LifeAsProject.R;
import com.MH83.LifeAsProject.custom.BitmapCustom;
import com.MH83.LifeAsProject.custom.GlobalClass;
import com.MH83.LifeAsProject.data.DBHelper;
import com.MH83.LifeAsProject.interfac.IFragmentViewCustom;
import com.MH83.LifeAsProject.interfac.enums.EnumStatus;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeAction;
import com.MH83.LifeAsProject.interfac.enums.EnumTypeDevice;
import com.MH83.LifeAsProject.model.Money.MoneyDream;
import com.MH83.LifeAsProject.view.Custom.CustomPopupBottomFragment;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;
import java.io.FileOutputStream;

public class FragmentMoneyDreamPopupNew extends CustomPopupBottomFragment<MoneyDream>
{
    //region Constants
    private static final int _layoutId = R.layout.popup_money_dream_new;
    //endregion

    //region Fragment parameters
    private Long _id;
    Integer _position;
    private String _imageUrl;
    String _imagePath;
    private boolean _existTarget = false;
    private int _countTarget;
    private EnumStatus _status;
    private boolean _archive = false;
    private int _dialogWidth = 1000;
    //endregion

    //region Fragment Components
    private EditText _editTextName;
    private SubsamplingScaleImageView _subsamplingScaleImageView;
    private ImageView _imageViewPhoto;
    private ImageView _imageViewArchive;
    //endregion

    //region Constructors
    public FragmentMoneyDreamPopupNew()
    {
        super(_layoutId,
                R.string.PopupDreamAdd,
                R.string.PopupDreamUpdate,
                R.string.errorUpdateMoneyDream);
    }

    public FragmentMoneyDreamPopupNew(AppCompatActivity activity,
                                      MoneyDream data,
                                      EnumTypeAction typeAction,
                                      IFragmentViewCustom<MoneyDream> iFragmentViewCustom)
    {
        super(activity, _layoutId, data,
                typeAction,
                null,
                null,
                R.string.PopupDreamAdd,
                R.string.PopupDreamUpdate,
                R.string.errorUpdateMoneyDream,
                iFragmentViewCustom,
                null,
                null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        globalVariable.set_popup(this);
    }
    //endregion

    //region Start Fragment
    @Override
    protected void FindComponents(View view)
    {
        super.FindComponents(view);

        _editTextName = view.findViewById(R.id.editTextName);
        _subsamplingScaleImageView = view.findViewById(R.id.subsamplingScaleImageView);
        _imageViewPhoto = view.findViewById(R.id.imageViewSelectPhoto);
        _imageViewArchive = view.findViewById(R.id.imageViewArchive);

        final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        boolean showInput = (globalVariable.get_typeDevice() == EnumTypeDevice.Tablet) ||
                getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        _focusedViewToAdd = _editTextName;
        _focusedViewToUpdate = _editTextName;
        _showInputToAdd = showInput;
        _showInputToUpdate = showInput;
    }

    @Override
    protected void SetComponentsSetting(View view)
    {
        super.SetComponentsSetting(view);

        GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
        if (globalVariable.get_hasUnpaidSubscription())
        {
            _imageViewArchive.setEnabled(false);
        }
    }

    @Override
    protected void SetDataToView()
    {
        super.SetDataToView();

        SetValues(new Object[] {_data} );
    }

    @Override
    protected void SetHeightComponents()
    {
        super.SetHeightComponents();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            int height = displaymetrics.heightPixels;
            height -= _layoutCaption.getLayoutParams().height;
            height -= 3 * getContext().getResources().getDimension(R.dimen.BasePadding);
            height -= _buttonSave.getLayoutParams().height;
            height -= _editTextName.getLayoutParams().height;
            height -= 4 * getContext().getResources().getDimension(R.dimen.BasePadding);

            ViewGroup.LayoutParams lp = _subsamplingScaleImageView.getLayoutParams();
            lp.height = height;
            _subsamplingScaleImageView.setLayoutParams(lp);

            _dialogWidth = (int) (4 * height / 3f);
        }
    }

    @Override
    public void PopupShow()
    {
        super.PopupShow();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            getDialog().getWindow().setLayout(_dialogWidth /*our width*/,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        else
        {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            getDialog().getWindow().setLayout((int) (0.95 * displaymetrics.widthPixels) /*our width*/, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
    //endregion

    //region Set Listeners
    @Override
    protected void SetListeners()
    {
        super.SetListeners();

        _subsamplingScaleImageView.setMaxScale(10);

        _imageViewPhoto.setOnClickListener(ShowPhotoSelect());
        _subsamplingScaleImageView.setOnClickListener(ShowPhotoSelect());

        _imageViewMascotToChange.setOnClickListener(view ->
                new FragmentMoneyDreamLegendToChangePopup((AppCompatActivity) getContext()).PopupShow());

        _imageViewArchive.setOnClickListener(view ->
        {
            _archive = !_archive;
            setArchiveImage(_archive);
        });
    }

    private View.OnClickListener ShowPhotoSelect()
    {
        return view ->
        {
            _editTextName.clearFocus();
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            getActivity().startActivityForResult(intent, REQUEST_GALLERY_FOR_MONEY_DREAM_POPUP);
        };
    }
    //endregion

    //region Check Params
    protected boolean ShowDeleteButton()
    {
        return _data != null && _data.canDelete(getContext());
    }

    protected boolean CheckFormValues()
    {
        return CheckName();
    }

    private boolean CheckName()
    {
        try
        {
            String name = getName();
            if (name.equals(""))
            {
                _editTextName.requestFocus();
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editTextName, InputMethodManager.SHOW_IMPLICIT);

                Toast.makeText(getActivity(),
                        getContext().getString(R.string.specifyName), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e)
        {
            _editTextName.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(_editTextName, InputMethodManager.SHOW_IMPLICIT);

            Toast.makeText(getActivity(),
                    getContext().getString(R.string.specifyCorrectName), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    protected boolean CheckFormValuesForDelete()
    {
        return true;
    }
    //endregion

    //region Set Params
    protected void SetValues(Object[] objects)
    {
        _data = (MoneyDream) objects[0];

        if (_data != null)
        {
            set_Id(_data.get_id());
            set_Position(_data.get_position());
            set_Name(_data.get_name());

            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (globalVariable.get_imagePath(false) != null)
                set_Image(globalVariable.get_imagePath(false), null);
            else
                set_Image(_data.get_imagePath(), _data.get_imageUrl());

            setExistAnyTarget(_data.getExistAnyTarget());
            setCountTarget(_data.get_countTarget());
            setStatus(_data.getStatus());
            setArchive(_data.is_archive());
        }
        else
        {
            set_Id(0L);
            set_Position(0);
            set_Name("");
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            if (globalVariable.get_imagePath(false) != null)
                set_Image(globalVariable.get_imagePath(false), null);

            setExistAnyTarget(false);
            setCountTarget(0);
            setStatus(EnumStatus.Blank);
            setArchive(false);
        }
    }

    private void setStatus(EnumStatus status) { _status = status; }

    private void setArchive(boolean archive)
    {
        _archive = archive;
        setArchiveImage(archive);
    }
    private void setArchiveImage(boolean archive)
    {
        if (archive)
            _imageViewArchive.setImageResource(R.drawable.icon_archive);
        else
            _imageViewArchive.setImageResource(R.drawable.icons_nonarchive);
    }

    private void setCountTarget(int countTarget) { _countTarget = countTarget; }

    private void setExistAnyTarget(boolean existTarget) { _existTarget = existTarget; }

    private void set_Id(Long id)
    {
        if (id != 0)
            _id = id;
    }

    private void set_Position(int position)
    {
        if (position != 0)
            _position = position;
    }

    private void set_Name(String name)
    {
        _editTextName.setText(name);
    }

    public void set_Image(String imagePath, String imageUrl)
    {
        _imagePath = imagePath;
        _imageUrl = imageUrl;

        if (_imagePath.equals(""))
            return;

        File filePath = new File(imagePath);
        try
        {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath.getPath());
            _subsamplingScaleImageView.setImage(ImageSource.bitmap(bitmap));
        }
        catch (Exception ex)
        {
            Log.d("ErrorSetImage", ex.getMessage());
        }
    }

    public void set_Image(Uri uri)
    {
        _subsamplingScaleImageView.setImage(ImageSource.uri(uri));

        File internalStorage = getContext().getDir(DBHelper.getDbName(getContext()) + "DreamPictures", Context.MODE_PRIVATE);
        File filePath = new File(internalStorage, "tmp.jpeg");
        String picturePath = filePath.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath))
        {
            Bitmap viewBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            viewBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();

            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_imagePath(picturePath);
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
            final GlobalClass globalVariable = (GlobalClass) getContext().getApplicationContext();
            globalVariable.set_imagePath(null);
        }
    }
    //endregion

    //region Get Params
    private Long get_Id()
    {
        try
        {
            return Long.parseLong(_id.toString());
        }
        catch (Exception e)
        {
            return -1L;
        }
    }

    private Integer getPosition()
    {
        try
        {
            return Integer.parseInt(_position.toString());
        }
        catch (Exception e)
        {
            try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
            {
                int position = dbHelper.QueryMoneyDreamMaxPosition() + 1;
                set_Position(position);
                return position;
            }
            catch (Exception e2)
            {
                return -1;
            }
        }
    }

    private String getName() throws Exception
    {
        try
        {
            return _editTextName.getText().toString();
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private String getImagePath()
    {
        Long id = get_Id();
        if (id == -1)
            try (DBHelper dbHelper = DBHelper.GetDBHelper(getContext()))
            {
                id = dbHelper.QueryMoneyDreamNextId();
            }
            catch (Exception e) { }

        Bitmap viewBitmap = BitmapCustom.createBitmapFromLayout(_subsamplingScaleImageView);

        File internalStorage = getContext().getDir(DBHelper.getDbName(getContext()) + "DreamPictures", Context.MODE_PRIVATE);
        File filePath = new File(internalStorage, id + ".jpeg");
        String picturePath = filePath.toString();

        try (FileOutputStream fos = new FileOutputStream(filePath))
        {
            viewBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*quality*/, fos);
            fos.close();
            return picturePath;
        }
        catch (Exception ex)
        {
            Log.i("DATABASE", "Problem updating picture", ex);
            return "";
        }
    }

    private String getImageUrl() throws Exception
    {
        try
        {
            return _imageUrl;
        }
        catch (Exception e) { throw new Exception(e); }
    }

    private boolean getExistAnyTarget() { return _existTarget; }
    private int getCountTarget() { return  _countTarget; }
    private EnumStatus getStatus() { return _status; }
    private boolean get_archive()
    {
        return _archive;
    }

    //endregion

    //region Save Params
    protected MoneyDream GetDataFromForm()
    {
        try
        {
            return new MoneyDream(get_Id(), getPosition(), getName(), getImagePath(),
                    getImageUrl(), getExistAnyTarget(), getCountTarget(), getStatus(),
                    get_archive());
        }
        catch (Exception e) { return null; }
    }
    //endregion
}