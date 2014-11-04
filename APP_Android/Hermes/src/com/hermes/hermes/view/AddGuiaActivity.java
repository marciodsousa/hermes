package com.hermes.hermes.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hermes.hermes.DatePickerFragment;
import com.hermes.hermes.R;
import com.hermes.hermes.SessionManager;
import com.hermes.hermes.TimePickerFragment;
import com.hermes.hermes.controller.ClienteController;
import com.hermes.hermes.controller.GuiaTransporteController;
import com.hermes.hermes.controller.LocalController;
import com.hermes.hermes.controller.ProdutoController;
import com.hermes.hermes.db.UtilizadorDBManager;
import com.hermes.hermes.model.TCliente;
import com.hermes.hermes.model.TGuiaTransporte;
import com.hermes.hermes.model.TLinhaProduto;
import com.hermes.hermes.model.TLocal;
import com.hermes.hermes.model.TProduto;
import com.hermes.hermes.model.TUtilizador;

public class AddGuiaActivity extends Activity {

	private CheckBox mMatricula;
	private EditText mClienteView;
	private EditText mDataView;
	private EditText mTimeView;
	private EditText mCargaView;
	private EditText mDescargaView;

	private TProduto prod;
	private TGuiaTransporte guia;
	private TLocal locCarga, locDescarga;
	private TCliente cli;
	private TUtilizador user;

	private ProdutoController prdController;
	private ClienteController cliController;
	private LocalController locController;
	private UtilizadorDBManager dbUsrs;

	private GuiaTransporteController gtrController;

	private List<TProduto> prods;
	private List<TLocal> locais;
	private List<TCliente> clis;
	private ArrayList<TLinhaProduto> lprods;

	private int quantidade;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_guide);

		guia = new TGuiaTransporte();
		gtrController = new GuiaTransporteController(getApplicationContext());

		prdController = new ProdutoController(getApplicationContext());
		cliController = new ClienteController(getApplicationContext());
		locController = new LocalController(getApplicationContext());

		dbUsrs = UtilizadorDBManager.getInstance(getApplicationContext());

		prods = prdController.getAllActiveProducts();
		locais = locController.getAllActivePlaces();
		clis = cliController.getAllActiveClients();
		lprods = new ArrayList<TLinhaProduto>();

		mMatricula = (CheckBox) findViewById(R.id.cbMatricula);
		mClienteView = (EditText) findViewById(R.id.viewCliente);
		mDataView = (EditText) findViewById(R.id.viewData);
		mTimeView = (EditText) findViewById(R.id.viewHora);
		mCargaView = (EditText) findViewById(R.id.viewLocCarga);
		mDescargaView = (EditText) findViewById(R.id.viewLocDescarga);

		// Client picker
		mClienteView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showClientPickerDialog(v);
			}
		});
		mClienteView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					showClientPickerDialog(v);
				}
			}
		});

		// Date picker
		mDataView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePickerDialog(v);
			}
		});
		mDataView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					showDatePickerDialog(v);
				}
			}
		});

		// Time picker
		mTimeView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTimePickerDialog(v);
			}
		});
		mTimeView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					showTimePickerDialog(v);
				}
			}
		});

		// Local Carga picker
		mCargaView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showPlacesPickerDialog(v, true);
			}
		});
		mCargaView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					showPlacesPickerDialog(v, true);
				}
			}
		});

		// Local Descarga picker
		mDescargaView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showPlacesPickerDialog(v, false);
			}
		});
		mDescargaView
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							showPlacesPickerDialog(v, false);
						}
					}
				});

		findViewById(R.id.btnSubmit).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (validateFields()) {

							SessionManager sm = SessionManager
									.getInstance(getApplicationContext());
							HashMap<String, String> userData = sm
									.getUserDetails();
							user = dbUsrs.getUtilizadorById(Integer
									.parseInt(userData.get(sm.KEY_USERID)));

							guia.setUtilizador(user);
							guia.setCLiente(cli);
							guia.setDataTransporte(mDataView.getText()
									.toString());
							guia.setHoraTransporte(mTimeView.getText()
									.toString());
							// guia.setMatricula(matricula);
							guia.setProds(lprods);
							guia.setLocalCarga(locCarga);
							guia.setLocalDescarga(locDescarga);
							guia.setEstado(1);

							if (mMatricula.isChecked())
								guia.setMatricula(userData
										.get(sm.KEY_MATRICULA));

							if (gtrController.saveGuiaTransporte(guia))
								finish();
						}
					}
				});

		findViewById(R.id.btnCancel).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						finish();
					}
				});

		findViewById(R.id.btnAddProduct).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						final CharSequence[] prodsStrings = new CharSequence[prods
								.size()];

						for (int i = 0; i < prods.size(); i++) {
							prodsStrings[i] = prods.get(i).getNome();
						}

						// TODO Auto-generated method stub

						AlertDialog.Builder builder3 = new AlertDialog.Builder(
								AddGuiaActivity.this);

						builder3.setTitle("Seleccione o Produto").setItems(
								prodsStrings,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										prod = prods.get(which);

										TableLayout ll = (TableLayout) findViewById(R.id.tblLayout);

										TableRow row = new TableRow(
												AddGuiaActivity.this);
										TableRow.LayoutParams lp = new TableRow.LayoutParams(
												TableRow.LayoutParams.WRAP_CONTENT);
										TextView txtQnt = new TextView(
												AddGuiaActivity.this);
										txtQnt.setText(quantidade + "");

										TextView txtPrd = new TextView(
												AddGuiaActivity.this);
										txtPrd.setText(prod.getNome());

										TextView txtVl = new TextView(
												AddGuiaActivity.this);
										Double preco = (double) prod
												.getValUnitario();
										txtVl.setText(preco / 100 + "€");

										row.setLayoutParams(lp);
										row.addView(txtQnt);
										row.addView(txtPrd);
										row.addView(txtVl);
										ll.addView(row, 1);

										TLinhaProduto lprod = new TLinhaProduto();
										lprod.setProduto(prod);
										lprod.setQuantidade(quantidade);
										lprod.setValorAtual(prod.getValUnitario()*quantidade);

										lprods.add(lprod);

									}
								});
						builder3.show();

						showQuantityInputDialog(view);

					}
				});

	}

	private boolean validateFields() {
		boolean ret = false;

		if (mClienteView.getText().toString().length() > 2) {
			if (isValidDate(mDataView.getText().toString(), mTimeView.getText().toString())) {
				if (mCargaView.getText().toString().length() > 2) {
					if (mDescargaView.getText().toString().length() > 2) {
						if (lprods.size() > 0)
							ret = true;
					}
				}
			}
		}

		return ret;
	}

	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy kk:mm");

	boolean isValidDate(String data, String hora) {
		try {
			format.parse(data + " " + hora);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public void showDatePickerDialog(View v) {
		DatePickerFragment newFragment = new DatePickerFragment();

		OnDateSetListener ondate = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {

				mDataView.setText(String.valueOf(dayOfMonth) + "/"
						+ String.valueOf(monthOfYear + 1) + "/"
						+ String.valueOf(year));
			}
		};

		newFragment.setCallBack(ondate);

		newFragment.show(getFragmentManager(), "datePicker");

	}
	
	public void showTimePickerDialog(View v) {
		TimePickerFragment newFragment = new TimePickerFragment();

		OnTimeSetListener ontime = new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hour, int minute) {

				mTimeView.setText(String.valueOf(hour) + ":"
						+ String.valueOf(minute));
			}
		};

		newFragment.setCallBack(ontime);

		newFragment.show(getFragmentManager(), "timePicker");

	}

	public void showClientPickerDialog(View v) {
		final CharSequence[] clisStrings = new CharSequence[clis.size()];

		for (int i = 0; i < clis.size(); i++) {
			clisStrings[i] = clis.get(i).getNome();
		}

		// TODO Auto-generated method stub

		AlertDialog.Builder builder3 = new AlertDialog.Builder(
				AddGuiaActivity.this);

		builder3.setTitle("Escolha o Cliente desejado").setItems(clisStrings,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mClienteView.setText(clisStrings[which]);
						cli = clis.get(which);
					}
				});
		builder3.show();

	}

	public void showPlacesPickerDialog(View v, boolean crg) {
		final boolean carga = crg;
		final CharSequence[] locaisStrings = new CharSequence[locais.size()];

		for (int i = 0; i < locais.size(); i++) {
			locaisStrings[i] = locais.get(i).getNome();
		}

		// TODO Auto-generated method stub

		AlertDialog.Builder builder3 = new AlertDialog.Builder(
				AddGuiaActivity.this);

		builder3.setTitle("Escolha o Local desejado").setItems(locaisStrings,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (carga) {
							mCargaView.setText(locaisStrings[which]);
							locCarga = locais.get(which);
						} else {
							mDescargaView.setText(locaisStrings[which]);
							locDescarga = locais.get(which);
						}

					}
				});
		builder3.show();
	}

	public void showQuantityInputDialog(View v) {
		final CharSequence[] locaisStrings = new CharSequence[locais.size()];

		for (int i = 0; i < locais.size(); i++) {
			locaisStrings[i] = locais.get(i).getNome();
		}

		// TODO Auto-generated method stub

		AlertDialog.Builder builder3 = new AlertDialog.Builder(
				AddGuiaActivity.this);

		final EditText input = new EditText(this);
		builder3.setView(input);
		builder3.setTitle("Quantidade");
		builder3.setMessage("Inserir quantidade do item:");

		builder3.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String qnt = input.getText().toString();
				quantidade = Integer.parseInt(qnt);
			}
		});

		builder3.show();
	}
}
