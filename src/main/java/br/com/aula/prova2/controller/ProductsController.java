package br.com.aula.prova2.controller;

import br.com.aula.prova2.dao.ProdutoDao;
import br.com.aula.prova2.model.ItemPedido;
import br.com.aula.prova2.model.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsController {

    @FXML private TableView<Produto> productTable;
    @FXML private TableColumn<Produto, Integer> colId;
    @FXML private TableColumn<Produto, String> colName;
    @FXML private TableColumn<Produto, String> colDesc;
    @FXML private TableColumn<Produto, BigDecimal> colPrice;

    private final ProdutoDao produtoDao = new ProdutoDao();
    private final List<ItemPedido> cart = new ArrayList<>();

    @FXML
    public void initialize() {

        colId   .setCellValueFactory(new PropertyValueFactory<>("id"));
        colName .setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDesc .setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("preco"));

        try {
            ObservableList<Produto> produtos =
                    FXCollections.observableArrayList(produtoDao.findAll());
            productTable.setItems(produtos);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao carregar produtos: " + e.getMessage());
        }
    }


    @FXML
    private void onAddToCart(ActionEvent event) {
        Produto selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selecione um produto primeiro.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Quantidade");
        dialog.setHeaderText("Informe a quantidade de '" + selected.getNome() + "'");
        dialog.setContentText("Quantidade:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(qtdStr -> {
            try {
                int quantidade = Integer.parseInt(qtdStr);
                if (quantidade <= 0) throw new NumberFormatException();

                ItemPedido item = new ItemPedido();
                item.setProdutoId(selected.getId());
                item.setQuantidade(quantidade);
                item.setPrecoUnitario(selected.getPreco());
                item.setPedidoId(0);

                cart.add(item);
                showAlert(Alert.AlertType.INFORMATION,
                        quantidade + "x '" + selected.getNome() + "' adicionado(s) ao carrinho.");

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Quantidade inválida.");
            }
        });
    }

    public List<ItemPedido> getCart() {
        return cart;
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
