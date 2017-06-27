package me.inventosachingupta;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.inventosachingupta.dao.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by InventoSachin on 22-06-2017.
 */
public class HomeController {

    String invoiceNO;

    Context context=Context.getInstance();
    int quant;

    String pname,pdod,pdiag,psex,page;


    ObservableList<Employee> employeeTableData = FXCollections.observableArrayList();
    ObservableList<Patient> patientTableData = FXCollections.observableArrayList();
    ObservableList<Products> productTableData = FXCollections.observableArrayList();
    ObservableList<Sale> saleTableData = FXCollections.observableArrayList();
    Connection connection;
    {
        connection = DataBase.getConnection();
    }
   public  String cat;


    @FXML private StackPane dData;
    @FXML private Pane vSalePane,veEmployeePane,vProductPane,vPatientPane,salePane,iPatient,iEmployee,iMedicine,eMedicineUpdate,eMedicineDelete,eEmployeeUpdate,eEmployeeDelete;
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableView<Patient> patientTable;

    @FXML private MenuItem aboutMenu,deleteEmployeeMenuitem,updateEmployeeMenuitem,addEmployeeMenuitem,seprateMenuItem;
    @FXML private TableColumn<Employee,String> etid,etname,etcontact,etjobtitle,etemail,etdepartment,etshift,ettype,etaddress,etsdate,etedate;
    @FXML private TableColumn<Patient,String> ptid,ptname,ptdob,ptage,ptdoc,ptgender,ptdiagnosis;
    @FXML private TableColumn<SoldItem,String> ssn,siname,sic,ssf,sbn,sed;
    @FXML private TableColumn<SoldItem,Float> sq,smrp,samt;
    @FXML private TableColumn<Products,String> pdtcode,pdtname,pdtbname,pdtCategory,pdtdepartname,pdtpsize,pdtptype,pdtquantity;
    @FXML private TableColumn<Sale,String> sdtino,sdtcid,sdtcname,sdtnoi,sdtquant,sdtamt,sdtmop;

    @FXML private TextArea apDiagnosis;
    @FXML private ComboBox<String> apSex;
    @FXML private TextField apId,apName;

    public void initialize(){
        setColumnTypes();
        System.out.println(Context.getInstance().getCat());
        cat=Context.getInstance().getCat();
        if(!cat.equalsIgnoreCase("admin")){
            //seprateMenuItem.setDisable(true);
            seprateMenuItem.setVisible(false);
            updateEmployeeMenuitem.setVisible(false);
            addEmployeeMenuitem.setVisible(false);
            deleteEmployeeMenuitem.setVisible(false);
        }
        try {
            ResultSet rs=connection.createStatement().executeQuery("Select emp_id,emp_name,emp_phone,emp_email,emp_job_title,emp_department,emp_shift,emp_type,emp_address,emp_start_date,emp_end_date  from employeedata");
            while (rs.next()){
                Employee row=new Employee(rs.getString(1),rs.getString(2),String.valueOf(rs.getLong(3)),rs.getString(4)
                        ,rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11));
                employeeTableData.add(row);
            }
            rs=connection.createStatement().executeQuery("Select * from PatientDetails");
            while(rs.next()){
                Patient pat=new Patient(rs.getString(1),rs.getString(2),rs.getString(3),String.valueOf(rs.getInt(4)),rs.getString(5),rs.getString(6),rs.getString(7));
                patientTableData.add(pat);
            }
            rs=connection.createStatement().executeQuery("Select * from ProductsDetails");
            while(rs.next()){
                Products prod=new Products(rs.getString(1),rs.getString(2),rs.getString(3),String.valueOf(rs.getInt(4)),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
                productTableData.add(prod);
            }
            rs=connection.createStatement().executeQuery("Select * from Sale");
            while(rs.next()){
                Sale pat=new Sale(rs.getString(1),rs.getString(2),rs.getString(3),String.valueOf(rs.getInt(4)),rs.getString(5),rs.getString(6),rs.getString(7));
                saleTableData.add(pat);
            }


            employeeTable.setItems(employeeTableData);
            patientTable.setItems(patientTableData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setColumnTypes(){
        sdtino.setCellValueFactory(new PropertyValueFactory<Sale,String>("invoiceno"));
        sdtcid.setCellValueFactory(new PropertyValueFactory<Sale,String>("customerid"));
        sdtcname.setCellValueFactory(new PropertyValueFactory<Sale,String>("customername"));
        sdtnoi.setCellValueFactory(new PropertyValueFactory<Sale,String>("noofitems"));
        sdtquant.setCellValueFactory(new PropertyValueFactory<Sale,String>("quantity"));
        sdtamt.setCellValueFactory(new PropertyValueFactory<Sale,String>("amt"));
        sdtmop.setCellValueFactory(new PropertyValueFactory<Sale,String>("paymethod"));

        ptid.setCellValueFactory(new PropertyValueFactory<Patient, String>("id"));
        ptname.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));
        ptdob.setCellValueFactory(new PropertyValueFactory<Patient,String>("dob"));
        ptage.setCellValueFactory(new PropertyValueFactory<Patient,String>("age"));
        ptdoc.setCellValueFactory(new PropertyValueFactory<Patient,String>("doctor_name"));
        ptgender.setCellValueFactory(new PropertyValueFactory<Patient,String>("gender"));
        ptdiagnosis.setCellValueFactory(new PropertyValueFactory<Patient,String>("diagnosis"));

        etid.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
        etname.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        etcontact.setCellValueFactory(new PropertyValueFactory<Employee, String>("contact"));
        etdepartment.setCellValueFactory(new PropertyValueFactory<Employee, String>("department"));
        etaddress.setCellValueFactory(new PropertyValueFactory<Employee, String>("address"));
        etsdate.setCellValueFactory(new PropertyValueFactory<Employee, String>("start_date"));
        etedate.setCellValueFactory(new PropertyValueFactory<Employee, String>("end_date"));
        ettype.setCellValueFactory(new PropertyValueFactory<Employee, String>("type"));
        etshift.setCellValueFactory(new PropertyValueFactory<Employee, String>("shift"));
        etjobtitle.setCellValueFactory(new PropertyValueFactory<Employee, String>("job_title"));
        etemail.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));

        pdtcode.setCellValueFactory(new PropertyValueFactory<Products,String>("code"));
        pdtname.setCellValueFactory(new PropertyValueFactory<Products,String>("name"));
        pdtCategory.setCellValueFactory(new PropertyValueFactory<Products,String>("category"));
        pdtbname.setCellValueFactory(new PropertyValueFactory<Products,String>("bname"));
        pdtdepartname.setCellValueFactory(new PropertyValueFactory<Products,String>("department"));
        pdtpsize.setCellValueFactory(new PropertyValueFactory<Products,String>("packsize"));
        pdtptype.setCellValueFactory(new PropertyValueFactory<Products,String>("type"));
        pdtquantity.setCellValueFactory(new PropertyValueFactory<Products,String>("quant"));

        ssn.setCellValueFactory(new PropertyValueFactory<SoldItem,String>(""));
        sic.setCellValueFactory(new PropertyValueFactory<SoldItem,String>("icode"));
        siname.setCellValueFactory(new PropertyValueFactory<SoldItem,String>("iname"));
        ssf.setCellValueFactory(new PropertyValueFactory<SoldItem,String>("formulation"));
        sbn.setCellValueFactory(new PropertyValueFactory<SoldItem,String>("batchno"));
        sq.setCellValueFactory(new PropertyValueFactory<SoldItem,Float>("quant"));
        smrp.setCellValueFactory(new PropertyValueFactory<SoldItem,Float>("mrp"));
        samt.setCellValueFactory(new PropertyValueFactory<SoldItem,Float>("amt"));
        sed.setCellValueFactory(new PropertyValueFactory<SoldItem,String>("edate"));
    }

    private void reteriveDataToObjects(){
        try {
            if (veEmployeePane.isVisible()) {
                employeeTableData.clear();
                ResultSet rs=connection.createStatement().executeQuery("Select emp_id,emp_name,emp_phone,emp_email,emp_job_title,emp_department,emp_shift,emp_type,emp_address,emp_start_date,emp_end_date  from employeedata");
                while (rs.next()){
                    Employee row=new Employee(rs.getString(1),rs.getString(2),String.valueOf(rs.getLong(3)),rs.getString(4)
                            ,rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11));
                    employeeTableData.add(row);
                }
            }
            if (vPatientPane.isVisible()) {
                patientTableData.clear();
                ResultSet rs=connection.createStatement().executeQuery("Select * from PatientDetails");
                while(rs.next()){
                    Patient pat=new Patient(rs.getString(1),rs.getString(2),rs.getString(3),String.valueOf(rs.getInt(4)),rs.getString(5),rs.getString(6),rs.getString(7));
                    patientTableData.add(pat);
                }
            }
            if (vProductPane.isVisible()) {

            }
            if (vSalePane.isVisible()) {
                saleTableData.clear();
                ResultSet rs=connection.createStatement().executeQuery("Select * from Sale");
                while(rs.next()){
                    Sale pat=new Sale(rs.getString(1),rs.getString(2),rs.getString(3),String.valueOf(rs.getInt(4)),rs.getString(5),rs.getString(6),rs.getString(7));
                    saleTableData.add(pat);
                }
            }
        }catch(Exception ex){}
    }

    @FXML public  void createSale(){

    }

    @FXML public void addEmployee(){

    }
    @FXML public void updateEmployee(){

    }

    @FXML public void addPatient(){

    }
    @FXML public void updatePaitent(){

    }

    @FXML public void addMedicine(){

    }
    @FXML public void updateMedicine(){

    }

    public void searchData(){

    }

    /**
     * MenuItem Functions
     */
    @FXML public void viewSaleAction(){
        changeTop(vSalePane);
    }
    @FXML public void viewPatientAction(){
        changeTop(vPatientPane);
    }
    @FXML public void viewEmployeeAction(){
        changeTop(veEmployeePane);
    }
    @FXML public void viewProductAction(){
        changeTop(vProductPane);
    }
    @FXML public void createSaleAction(){
        changeTop(salePane);
    }
    @FXML public void aboutMenuAcion() throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("about.fxml"));
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    @FXML public void insertEmployeeMenuAcion(){changeTop(iEmployee);}
    @FXML public void insertMedicineMenuAcion(){changeTop(iMedicine);}
    @FXML public void insertPatientMenuAcion(){changeTop(iPatient);}
    @FXML public void updateEmployeeMenuAcion(){changeTop(eEmployeeUpdate);}
    @FXML public void updateMedicineMenuAcion(){changeTop(eMedicineUpdate);}
    @FXML public void deleteEmployeeMenuAcion(){changeTop(eEmployeeDelete);}
    @FXML public void deleteMedicineMenuAcion(){changeTop(eMedicineDelete);}
    @FXML public void exit(){
        System.exit(0);
    }

    /**
     *Used to change the nodes of the stackpane using
     * @param node
     */
    private void changeTop(Node node) {
        ObservableList<Node> childs = dData.getChildren();

        Node topNode = childs.get(childs.size()-1);
        Node newTopNode = node;

        double width = dData.getWidth();
        KeyFrame start = new KeyFrame(Duration.ZERO,
                new KeyValue(newTopNode.translateXProperty(), width),
                new KeyValue(topNode.translateXProperty(), 0));
        KeyFrame end = new KeyFrame(Duration.seconds(2),
                new KeyValue(newTopNode.translateXProperty(), 0),
                new KeyValue(topNode.translateXProperty(), -width));
        Timeline slide = new Timeline(start, end);
        slide.setOnFinished(e -> {
            topNode.toBack();
            topNode.setVisible(false);
            newTopNode.toFront();
            newTopNode.setVisible(true);

        });
        slide.play();
    }

}