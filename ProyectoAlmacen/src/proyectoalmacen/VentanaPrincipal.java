package proyectoalmacen;

import Persistencia.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javafx.geometry.Pos;

public class VentanaPrincipal extends Application {

    private Almacen<Buloneria> miAlmacen = new Almacen<>();
    private ObservableList<Buloneria> listaObservable;
    private TableView<Buloneria> tabla;

    // Campos Pestaña AGREGAR
    private ComboBox<String> cbTipoAdd;
    private TextField txtIdAdd, txtNomAdd, txtStAdd, txtPrAdd, txtDiametroAdd, txtLongitudAdd;
    private ComboBox<TipoTratamiento> cbTratAdd;
    private ComboBox<TipoRosca> cbRosAdd;
    private ComboBox<TipoArandela> cbAraAdd;
    
    
    // Campos Pestaña GESTIONAR (Edit/Del)
    private TextField txtIdEdit, txtNomEdit, txtStEdit, txtPrEdit, txtDiametroEdit, txtLongitudEdit;
    private ComboBox<TipoTratamiento> cbTratEdit;
    private ComboBox<TipoRosca> cbRosEdit;
    private ComboBox<TipoArandela> cbAraEdit;
    private Label lblTipoSeleccionado;
    private CheckBox chkConfirmarAdd, chkConfirmarGestion;

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        // Carga inicial
        miAlmacen.getLista().addAll(PersistenciaBinaria.cargar());
        listaObservable = FXCollections.observableArrayList(miAlmacen.getLista());

        // --- TABLA (FIJA EN EL CENTRO) ---
        tabla = new TableView<>(listaObservable);
        configurarColumnas();
        
        // Listener: Cuando selecciono algo en la tabla, se cargan los datos en la pestaña Gestionar
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cargarDatosEnFormularioGestion(newSelection);
            }
        });

        // --- PANEL DE PESTAÑAS (ABAJO) ---
        TabPane tabPane = new TabPane();
        Tab tAdd = new Tab("➕ Agregar Nuevo", crearPanelAgregar());
        Tab tEdit = new Tab("⚙️ Gestionar Seleccionado", crearPanelGestionar());
        Tab tFiltros = new Tab("🔍 Filtros y Orden", crearPanelFiltros());
        tabPane.getTabs().addAll(tAdd, tEdit, tFiltros);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // BOTÓN GUARDAR GLOBAL
        Button btnGuardarTodo = new Button("GUARDAR CAMBIOS");
        btnGuardarTodo.setMaxWidth(Double.MAX_VALUE);
        btnGuardarTodo.setOnAction(e -> {
            PersistenciaBinaria.guardar(miAlmacen.getLista());
            PersistenciaCSV.guardarCSV(miAlmacen.getLista());
            PersistenciaJSON.guardarJSON(miAlmacen.getLista(), "src/datos/datos.json");
            mostrarAlerta("Éxito", "Archivos actualizados correctamente.");
        });

        // LAYOUT PRINCIPAL
        VBox inferior = new VBox(5, tabPane, btnGuardarTodo);
        BorderPane root = new BorderPane();
        root.setCenter(tabla);
        root.setBottom(inferior);
        root.setPadding(new Insets(10));

        escenarioPrincipal.setScene(new Scene(root, 1000, 750));
        escenarioPrincipal.setTitle("Gestión de Stock Buloneria - Enzo");
        escenarioPrincipal.show();
    }

    // --- PESTAÑA AGREGAR ---
    private VBox crearPanelAgregar() {
    // 1. INICIALIZACIÓN (Asegurate de que estas variables estén declaradas arriba como atributos de clase)
    cbTipoAdd = new ComboBox<>(FXCollections.observableArrayList("Bulón", "Arandela", "Tuerca"));
    txtIdAdd = new TextField(); 
    txtNomAdd = new TextField(); 
    txtStAdd = new TextField(); 
    txtPrAdd = new TextField();
    txtDiametroAdd = new TextField(); 
    txtLongitudAdd = new TextField();
    cbTratAdd = new ComboBox<>(FXCollections.observableArrayList(TipoTratamiento.values()));
    cbRosAdd = new ComboBox<>(FXCollections.observableArrayList(TipoRosca.values()));
    cbAraAdd = new ComboBox<>(FXCollections.observableArrayList(TipoArandela.values()));
    chkConfirmarAdd = new CheckBox("Confirmo que los datos son correctos");
    Button btnAdd = new Button("Confirmar Alta");

    // 2. CONFIGURACIÓN INICIAL DE VISIBILIDAD
    txtDiametroAdd.setVisible(false); txtDiametroAdd.setManaged(false);
    txtLongitudAdd.setVisible(false); txtLongitudAdd.setManaged(false);
    cbRosAdd.setVisible(false); cbRosAdd.setManaged(false);
    cbAraAdd.setVisible(false); cbAraAdd.setManaged(false);

    cbTipoAdd.setOnAction(e -> {
        String sel = cbTipoAdd.getValue();
        if (sel == null) return;
        txtDiametroAdd.setVisible(true); txtDiametroAdd.setManaged(true);
        txtLongitudAdd.setVisible(sel.equals("Bulón")); 
        txtLongitudAdd.setManaged(sel.equals("Bulón"));
        cbRosAdd.setVisible(sel.equals("Bulón") || sel.equals("Tuerca")); 
        cbRosAdd.setManaged(sel.equals("Bulón") || sel.equals("Tuerca"));
        cbAraAdd.setVisible(sel.equals("Arandela")); 
        cbAraAdd.setManaged(sel.equals("Arandela"));
    });

    btnAdd.setOnAction(e -> ejecutarAlta());

    // 3. ARMADO DEL GRID (REVISÁ QUE NO HAYA LINEAS REPETIDAS AQUÍ)
    GridPane g = new GridPane();
    g.setHgap(10); g.setVgap(8); g.setPadding(new Insets(15));

    g.add(new Label("Tipo de Material:"), 0, 0); g.add(cbTipoAdd, 1, 0);
    g.add(new Label("ID:"), 0, 1);                g.add(txtIdAdd, 1, 1);
    g.add(new Label("Nombre:"), 2, 1);            g.add(txtNomAdd, 3, 1);
    g.add(new Label("Stock:"), 0, 2);              g.add(txtStAdd, 1, 2);
    g.add(new Label("Precio:"), 2, 2);            g.add(txtPrAdd, 3, 2);
    g.add(new Label("Tratamiento:"), 0, 3);        g.add(cbTratAdd, 1, 3);
    
    // Estos campos aparecen y desaparecen pero ya están en el grid:
    g.add(new Label("Diámetro:"), 0, 4);          g.add(txtDiametroAdd, 1, 4);
    g.add(new Label("Longitud:"), 2, 4);          g.add(txtLongitudAdd, 3, 4);
    g.add(new Label("Rosca:"), 0, 5);              g.add(cbRosAdd, 1, 5);
    g.add(new Label("Tipo Arandela:"), 2, 5);      g.add(cbAraAdd, 3, 5);
    
    // Checkbox de confirmación y Botón (usando span para que ocupen lugar)
    g.add(chkConfirmarAdd, 1, 6, 2, 1); 
    g.add(btnAdd, 1, 7);

    return new VBox(g);
}

    // --- PESTAÑA GESTIONAR (MODIFICAR/ELIMINAR) ---
    private VBox crearPanelGestionar() {
    lblTipoSeleccionado = new Label("Seleccione un ítem en la tabla para editar");
    lblTipoSeleccionado.setStyle("-fx-font-weight: bold; -fx-text-fill: #2196F3;");

    txtIdEdit = new TextField(); txtIdEdit.setEditable(false); 
    txtNomEdit = new TextField(); txtStEdit = new TextField();
    txtPrEdit = new TextField(); 
    
    txtDiametroEdit = new TextField();
    txtLongitudEdit = new TextField();
    cbTratEdit = new ComboBox<>(FXCollections.observableArrayList(TipoTratamiento.values()));
    cbRosEdit = new ComboBox<>(FXCollections.observableArrayList(TipoRosca.values()));
    cbAraEdit = new ComboBox<>(FXCollections.observableArrayList(TipoArandela.values()));

    // --- AQUÍ ESTÁ EL COMPONENTE QUE FALTABA ---
    chkConfirmarGestion = new CheckBox("Confirmar cambios o eliminación");
    chkConfirmarGestion.setStyle("-fx-text-fill: #d32f2f; -fx-font-weight: bold;"); 

    Button btnUpdate = new Button("💾 Guardar Cambios");
    btnUpdate.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
    btnUpdate.setOnAction(e -> ejecutarActualizacion());

    Button btnDelete = new Button("❌ Eliminar");
    btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
    btnDelete.setOnAction(e -> ejecutarBaja());

    GridPane g = new GridPane(); 
    g.setHgap(10); g.setVgap(8); g.setPadding(new Insets(15));
    
    g.add(lblTipoSeleccionado, 0, 0, 4, 1);
    g.add(new Label("ID:"), 0, 1); g.add(txtIdEdit, 1, 1);
    g.add(new Label("Nombre:"), 2, 1); g.add(txtNomEdit, 3, 1);
    g.add(new Label("Stock:"), 0, 2); g.add(txtStEdit, 1, 2);
    g.add(new Label("Precio:"), 2, 2); g.add(txtPrEdit, 3, 2);
    g.add(new Label("Tratamiento:"), 0, 3); g.add(cbTratEdit, 1, 3);
    
    g.add(new Label("Diámetro:"), 0, 4); g.add(txtDiametroEdit, 1, 4);
    g.add(new Label("Longitud:"), 2, 4); g.add(txtLongitudEdit, 3, 4);
    g.add(new Label("Rosca:"), 0, 5); g.add(cbRosEdit, 1, 5);
    g.add(new Label("Tipo Arand.:"), 2, 5); g.add(cbAraEdit, 3, 5);
    
    // Agregamos el CheckBox al final del formulario
    g.add(chkConfirmarGestion, 1, 6, 2, 1); 

    HBox botones = new HBox(15, btnUpdate, btnDelete);
    botones.setPadding(new Insets(10));
    botones.setAlignment(Pos.CENTER_LEFT);
    
    return new VBox(10, g, botones);
}

    // --- PESTAÑA FILTROS Y ORDENAMIENTO ---
    private VBox crearPanelFiltros() {
        // --- 1. SECCIÓN DE ORDENAMIENTO ---
        Button bOrdNombre = new Button("Ordenar por Nombre (A-Z)");
        bOrdNombre.setOnAction(e -> { 
            miAlmacen.getLista().sort((x, y) -> x.getNombre().compareToIgnoreCase(y.getNombre())); 
            actualizarVista(); 
        });

        Button bOrdStock = new Button("Ordenar por Stock (-/+)");
        bOrdStock.setOnAction(e -> { 
            miAlmacen.getLista().sort(Comparator.comparingInt(Buloneria::getStock)); 
            actualizarVista(); 
        });

        Button bOrdPrecio = new Button("Ordenar por Precio (-/+)");
        bOrdPrecio.setOnAction(e -> { 
            miAlmacen.getLista().sort(Comparator.comparingDouble(Buloneria::getPrecioUtil)); 
            actualizarVista(); 
        });

        HBox filaOrden = new HBox(10, new Label("Ordenar Inventario:"), bOrdNombre, bOrdStock, bOrdPrecio);
        filaOrden.setAlignment(Pos.CENTER_LEFT);

        // --- 2. SECCIÓN DE FILTRADO ---
        ComboBox<String> cbFiltroMaterial = new ComboBox<>(FXCollections.observableArrayList("Todos", "Bulón", "Arandela", "Tuerca"));
        cbFiltroMaterial.setValue("Todos"); // Valor por defecto

        ComboBox<TipoTratamiento> cbFiltroTratamiento = new ComboBox<>(FXCollections.observableArrayList(TipoTratamiento.values()));
        cbFiltroTratamiento.setPromptText("Elegir Tratamiento...");

        Button btnAplicarFiltro = new Button("🔍 Aplicar Filtros");
        btnAplicarFiltro.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnAplicarFiltro.setOnAction(e -> {
            String materialSel = cbFiltroMaterial.getValue();
            TipoTratamiento tratSel = cbFiltroTratamiento.getValue();

            // Usamos Streams e Interfaces Funcionales (Lambdas) para filtrar la lista original
            List<Buloneria> filtrados = miAlmacen.getLista().stream()
                .filter(b -> {
                    // Filtro por material (Usando instanceof)
                    if (materialSel.equals("Todos")) return true;
                    if (materialSel.equals("Bulón") && b instanceof Bulon) return true;
                    if (materialSel.equals("Arandela") && b instanceof Arandela) return true;
                    if (materialSel.equals("Tuerca") && b instanceof Tuerca) return true;
                    return false;
                })
                .filter(b -> {
                    // Filtro por tratamiento (Si es null, pasa todo)
                    return tratSel == null || b.getTipoTratamiento() == tratSel;
                })
                .collect(Collectors.toList()); // Convertimos el resultado a una nueva lista

            // Actualizamos la tabla solo con los resultados filtrados
            tabla.setItems(FXCollections.observableArrayList(filtrados));
        });

        Button btnLimpiar = new Button("❌ Quitar Filtros");
        btnLimpiar.setOnAction(e -> {
            cbFiltroMaterial.setValue("Todos");
            cbFiltroTratamiento.setValue(null);
            actualizarVista(); // Vuelve a mostrar todo el almacén
        });

        HBox filaFiltro = new HBox(10, new Label("Material:"), cbFiltroMaterial, 
                                       new Label("Tratamiento:"), cbFiltroTratamiento, 
                                       btnAplicarFiltro, btnLimpiar);
        filaFiltro.setAlignment(Pos.CENTER_LEFT);

        // Armamos el panel completo
        VBox layout = new VBox(20, filaOrden, filaFiltro);
        layout.setPadding(new Insets(20));
        return layout;
    }

    // --- LÓGICA ---

    private void cargarDatosEnFormularioGestion(Buloneria item) {
    lblTipoSeleccionado.setText("Editando " + item.getClass().getSimpleName() + ": " + item.getNombre());
    txtIdEdit.setText(String.valueOf(item.getId()));
    txtNomEdit.setText(item.getNombre());
    txtStEdit.setText(String.valueOf(item.getStock()));
    txtPrEdit.setText(String.valueOf(item.getPrecioUtil()));
    cbTratEdit.setValue(item.getTipoTratamiento());

    // Lógica de visibilidad y carga de datos específicos
    boolean esBulon = item instanceof Bulon;
    boolean esArandela = item instanceof Arandela;
    boolean esTuerca = item instanceof Tuerca;

    // Diámetro: Lo muestran los tres (Bulón, Tuerca, Arandela)
    txtDiametroEdit.setVisible(true); txtDiametroEdit.setManaged(true);
    
    // Longitud y Rosca: Solo Bulón y Tuerca (Rosca)
    txtLongitudEdit.setVisible(esBulon); txtLongitudEdit.setManaged(esBulon);
    cbRosEdit.setVisible(esBulon || esTuerca); cbRosEdit.setManaged(esBulon || esTuerca);
    
    // Tipo Arandela: Solo Arandela
    cbAraEdit.setVisible(esArandela); cbAraEdit.setManaged(esArandela);

    if (esBulon) {
        Bulon b = (Bulon) item;
        txtDiametroEdit.setText(String.valueOf(b.getDiametroMM()));
        txtLongitudEdit.setText(String.valueOf(b.getLongitud()));
        cbRosEdit.setValue(b.getTipoRosca());
    } else if (esArandela) {
        Arandela a = (Arandela) item;
        txtDiametroEdit.setText(String.valueOf(a.getDiametroInterior()));
        cbAraEdit.setValue(a.getTipoArandela());
    } else if (esTuerca) {
        Tuerca t = (Tuerca) item;
        txtDiametroEdit.setText(String.valueOf(t.getDiametroMM()));
        cbRosEdit.setValue(t.getTipoRosca());
    }
}

    private void ejecutarActualizacion() {
    Buloneria sel = tabla.getSelectionModel().getSelectedItem();
    if (sel == null) {
        mostrarAlerta("Aviso", "Seleccione un producto para modificar.");
        return;
    }

    try {
        // VALIDACIÓN DE CONFIRMACIÓN
        if (!chkConfirmarGestion.isSelected()) {
            throw new AccionNoConfirmadaException("Debe confirmar antes de sobreescribir los datos de: " + sel.getNombre());
        }

        // Si confirmó, actualizamos (usando los try-catch de formato y stock)
        sel.setNombre(txtNomEdit.getText());
        sel.setPrecioUtil(Double.parseDouble(txtPrEdit.getText()));
        sel.setTipoTratamiento(cbTratEdit.getValue());
        
        // Esta línea puede lanzar StockInsuficienteException (tu otra excepción propia)
        sel.actualizarStock(Integer.parseInt(txtStEdit.getText()));

        // Casting para datos específicos (Bulon, Arandela, Tuerca)
        if (sel instanceof Bulon b) {
            b.setDiametroMM(Integer.parseInt(txtDiametroEdit.getText()));
            b.setLongitud(Double.parseDouble(txtLongitudEdit.getText()));
            b.setTipoRosca(cbRosEdit.getValue());
        } else if (sel instanceof Arandela a) {
            a.setDiametroInterior(Integer.parseInt(txtDiametroEdit.getText()));
            a.setTipoArandela(cbAraEdit.getValue());
        } else if (sel instanceof Tuerca t) {
            t.setDiametroMM(Integer.parseInt(txtDiametroEdit.getText()));
            t.setTipoRosca(cbRosEdit.getValue());
        }

        tabla.refresh();
        chkConfirmarGestion.setSelected(false);
        mostrarAlerta("Sincronizado", "Los datos se han actualizado correctamente.");

    } catch (AccionNoConfirmadaException | StockInsuficienteException e) {
        mostrarAlerta("Validación", e.getMessage());
    } catch (NumberFormatException e) {
        mostrarAlerta("Error de Formato", "Verifique que los valores numéricos sean válidos.");
    }
}

    private void ejecutarBaja() {
    try {
        // 1. Verificamos que haya algo seleccionado
        Buloneria sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarAlerta("Aviso", "Primero seleccione un material de la tabla.");
            return;
        }

        // 2. LANZAMOS EXCEPCIÓN PROPIA si no confirmó con el CheckBox
        if (!chkConfirmarGestion.isSelected()) {
            throw new AccionNoConfirmadaException("¡ALTO! Debe marcar la casilla de confirmación para eliminar: " + sel.getNombre());
        }

        // 3. Si pasó la validación, procedemos
        miAlmacen.eliminar(sel.getId());
        actualizarVista();
        
        // Limpiamos la selección y el check
        tabla.getSelectionModel().clearSelection();
        chkConfirmarGestion.setSelected(false);
        
        mostrarAlerta("Éxito", "El registro ha sido eliminado del inventario.");

    } catch (AccionNoConfirmadaException e) {
        // Atrapamos tu excepción de seguridad
        mostrarAlerta("Seguridad de Datos", e.getMessage());
    } catch (Exception e) {
        mostrarAlerta("Error", "Ocurrió un problema al intentar eliminar.");
    }
}

    private void ejecutarAlta() {
    try {
        // 1. CONFIRMACIÓN (Tu excepción propia)
        if (!chkConfirmarAdd.isSelected()) {
            throw new AccionNoConfirmadaException("Marcá la confirmación.");
        }

        // 2. VALIDACIÓN BASE (Lo que tienen todos)
        if (cbTipoAdd.getValue() == null || txtIdAdd.getText().isEmpty() || txtNomAdd.getText().isEmpty()) {
            throw new DatoInvalidoException("Faltan los datos básicos.");
        }

        String tipo = cbTipoAdd.getValue();
        int id = Integer.parseInt(txtIdAdd.getText());
        String n = txtNomAdd.getText();
        int s = Integer.parseInt(txtStAdd.getText());
        double p = Double.parseDouble(txtPrAdd.getText());
        TipoTratamiento tr = cbTratAdd.getValue();

        Buloneria nuevo = null;

        // 3. VALIDACIÓN POR TIPO (Solo valida lo que está visible)
        if (tipo.equals("Bulón")) {
            if (txtDiametroAdd.getText().isEmpty() || txtLongitudAdd.getText().isEmpty() || cbRosAdd.getValue() == null) {
                throw new DatoInvalidoException("Completá diámetro, longitud y rosca del Bulón.");
            }
            nuevo = new Bulon(Integer.parseInt(txtDiametroAdd.getText()), Double.parseDouble(txtLongitudAdd.getText()), cbRosAdd.getValue(), id, n, s, tr, p);
        } 
        else if (tipo.equals("Arandela")) {
            if (txtDiametroAdd.getText().isEmpty() || cbAraAdd.getValue() == null) {
                throw new DatoInvalidoException("Completá diámetro interior y tipo de Arandela.");
            }
            nuevo = new Arandela(Integer.parseInt(txtDiametroAdd.getText()), cbAraAdd.getValue(), id, n, s, tr, p);
        } 
        else if (tipo.equals("Tuerca")) {
            if (txtDiametroAdd.getText().isEmpty() || cbRosAdd.getValue() == null) {
                throw new DatoInvalidoException("Completá diámetro y rosca de la Tuerca.");
            }
            nuevo = new Tuerca(Integer.parseInt(txtDiametroAdd.getText()), cbRosAdd.getValue(), id, n, s, tr, p);
        }

        miAlmacen.agregar(nuevo);
        actualizarVista();
        limpiarCamposAdd();
        chkConfirmarAdd.setSelected(false);
        mostrarAlerta("Éxito", "Agregado correctamente.");

    } catch (AccionNoConfirmadaException | DatoInvalidoException e) {
        mostrarAlerta("Atención", e.getMessage());
    } catch (NumberFormatException e) {
        mostrarAlerta("Error", "Revisá que los números sean correctos (usá punto para decimales).");
    } catch (Exception e) {
        mostrarAlerta("Error crítico", e.getMessage());
    }
}

    private void configurarColumnas() {
        TableColumn<Buloneria, Integer> cId = new TableColumn<>("ID");
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Buloneria, String> cNom = new TableColumn<>("Nombre");
        cNom.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<Buloneria, Integer> cSt = new TableColumn<>("Stock");
        cSt.setCellValueFactory(new PropertyValueFactory<>("stock"));
        TableColumn<Buloneria, Double> cPr = new TableColumn<>("Precio");
        cPr.setCellValueFactory(new PropertyValueFactory<>("precioUtil"));
        tabla.getColumns().addAll(cId, cNom, cSt, cPr);
    }

    private void actualizarVista() {
        listaObservable.setAll(miAlmacen.getLista());
        tabla.setItems(listaObservable);
    }

    private void limpiarCamposAdd() {
    // Campos básicos
    txtIdAdd.clear();
    txtNomAdd.clear();
    txtStAdd.clear();
    txtPrAdd.clear();
    
    
    txtDiametroAdd.clear();
    txtLongitudAdd.clear();
    
    
    cbTipoAdd.setValue(null);
    cbTratAdd.setValue(null);
    cbRosAdd.setValue(null);
    cbAraAdd.setValue(null);
    chkConfirmarAdd.setSelected(false);
}

    private void mostrarAlerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION); a.setTitle(t); a.setHeaderText(null); a.setContentText(m); a.show();
    }

    public static void main(String[] args) { launch(args); }
}