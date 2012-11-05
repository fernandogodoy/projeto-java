package br.com.meuprimeiroprojeto.tablemodel;

import br.com.meuprimeiroprojeto.modelo.Estado;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Fernado
 */
public class TableModelEstado extends AbstractTableModel {

    private List<Estado> lista;

    public TableModelEstado(List<Estado> lista) {
        this.lista = lista;
    }

    public List<Estado> getLista() {
        return lista;
    }

    public void setLista(List<Estado> lista) {
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
        Estado obj = (Estado) getLista().get(rowIndex);
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
