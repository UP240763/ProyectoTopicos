package proyecto;

import javax.swing.*;

import proyecto.proveedor.PanelProveedores;
import proyecto.proveedor.ProveedorManager;
import proyecto.producto.PanelInventario;
import proyecto.producto.PanelProductos;
import proyecto.producto.ProductoManager;
import proyecto.usuario.PanelUsuarios;
import proyecto.usuario.Usuario;
import proyecto.usuario.UsuarioManager;
import proyecto.venta.PanelReportes;
import proyecto.venta.PanelVentas;
import proyecto.venta.VentaManager;

import java.awt.*;

public class SistemaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel panelCentral;

    private UsuarioManager usuarioManager = new UsuarioManager();
    private ProveedorManager proveedorManager = new ProveedorManager();
    ProductoManager productoManager = new ProductoManager();
    VentaManager ventaManager = new VentaManager(productoManager);

    public SistemaPrincipal() {
        setTitle("DAGNET - Sistema de Ventas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // ===== Usuario inicial por defecto =====
        usuarioManager.agregar(new Usuario("admin", "admin@gmail.com", "1234", "Administrador"));

        mostrarLogin();
    }

    private void construirMenuPorRol(String rol, JPanel menu) {
        if (rol.equalsIgnoreCase("Administrador")) {
            menu.add(crearBoton("Usuarios", "USUARIOS"));
            menu.add(crearBoton("Proveedores", "PROVEEDORES"));
            menu.add(crearBoton("Productos", "PRODUCTOS"));
            menu.add(crearBoton("Inventario", "INVENTARIO"));
            menu.add(crearBoton("Ventas", "VENTAS"));
            menu.add(crearBoton("Reportes", "REPORTES"));
        } else if (rol.equalsIgnoreCase("Empleado")) {
            menu.add(crearBoton("Productos", "PRODUCTOS"));
            menu.add(crearBoton("Inventario", "INVENTARIO"));
            menu.add(crearBoton("Ventas", "VENTAS"));
        }
    }

    private void mostrarLogin() {
        JPanel login = new JPanel(new GridBagLayout());
        login.setBackground(new Color(230, 230, 230));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);
        c.fill = GridBagConstraints.HORIZONTAL;

        // ===== TÍTULO =====
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1.0;

        JLabel lbl = new JLabel("INICIAR SESIÓN", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial Black", Font.BOLD, 60));
        lbl.setPreferredSize(new Dimension(600, 100));
        login.add(lbl, c);

        // USUARIO
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0;
        c.gridx = 0;
        JLabel uLabel = new JLabel("Usuario:");
        uLabel.setFont(new Font("Arial", Font.BOLD, 24));
        login.add(uLabel, c);

        c.gridx = 1;
        c.weightx = 1.0;
        JTextField txtU = new JTextField();
        txtU.setFont(new Font("Arial", Font.PLAIN, 22));
        txtU.setPreferredSize(new Dimension(400, 45));
        login.add(txtU, c);

        // CONTRASEÑA
        c.gridy = 2;
        c.gridx = 0;
        JLabel pLabel = new JLabel("Contraseña:");
        pLabel.setFont(new Font("Arial", Font.BOLD, 24));
        login.add(pLabel, c);

        c.gridx = 1;
        JPasswordField txtP = new JPasswordField();
        txtP.setFont(new Font("Arial", Font.PLAIN, 22));
        txtP.setPreferredSize(new Dimension(400, 45));
        login.add(txtP, c);

        // BOTÓN
        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 2;
        JButton btn = new JButton("Ingresar");
        btn.setBackground(new Color(25, 25, 255));
        btn.setForeground(Color.white);
        btn.setFont(new Font("Arial Black", Font.BOLD, 28));
        btn.setPreferredSize(new Dimension(300, 70));
        login.add(btn, c);

        btn.addActionListener(e -> {
            if (usuarioManager.login(txtU.getText(), new String(txtP.getPassword()))) {
                getContentPane().removeAll();
                getContentPane().revalidate();
                getContentPane().repaint();
                construirSistema();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }
        });

        setContentPane(login);
    }

    private void construirSistema() {
        JPanel header = new JPanel();
        header.setBackground(new Color(25, 25, 255));
        header.setPreferredSize(new Dimension(100, 60));

        JLabel t = new JLabel("DAGNET - Sistema de Ventas");
        t.setFont(new Font("Arial Black", Font.BOLD, 24));
        t.setForeground(Color.white);
        header.add(t);

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);

        panelCentral.add(new PanelUsuarios(usuarioManager), "USUARIOS");
        panelCentral.add(new PanelProveedores(proveedorManager), "PROVEEDORES");
        panelCentral.add(new PanelProductos(productoManager, proveedorManager), "PRODUCTOS");
        panelCentral.add(new PanelInventario(productoManager), "INVENTARIO");
        panelCentral.add(new PanelVentas(ventaManager, productoManager), "VENTAS");
        panelCentral.add(new PanelReportes(ventaManager), "REPORTES"); // solo ventaManager

        JPanel menu = new JPanel(new GridLayout(10, 1));
        menu.setPreferredSize(new Dimension(200, 0));
        menu.setBackground(new Color(200, 200, 200));

        String rol = usuarioManager.getUsuarioActual().getRol();
        construirMenuPorRol(rol, menu);

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(menu, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);
    }

    private JButton crearBoton(String nombre, String panel) {
        JButton b = new JButton(nombre);
        b.addActionListener(e -> cardLayout.show(panelCentral, panel));
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaPrincipal().setVisible(true));
    }
}
