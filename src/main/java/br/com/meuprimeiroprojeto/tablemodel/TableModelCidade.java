package br.com.meuprimeiroprojeto.tablemodel;

import br.com.meuprimeiroprojeto.modelo.Cidade;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Fernado
 */
public class TableModelCidade extends AbstractTableModel {

    private List<Cidade> lista;

    public TableModelCidade(List<Cidade> lista) {
        this.lista = lista;
    }

    public List<Cidade> getLista() {
        return lista;
    }

    public void setLista(List<Cidade> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        if (getLista() == null) {
            return 0;
        }
        return getLista().size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int column) {
        String[] coluna = {"Nome"};
        return coluna[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cidade obj = (Cidade) getLista().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return obj.getNome();

        }
        return obj;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
