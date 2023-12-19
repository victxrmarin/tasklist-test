package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskListApp extends JFrame {

    public DefaultListModel<Task> taskListModel;
    public JList<Task> taskList;
    public JTextField newTaskField;

    public TaskListApp() {
        // Configuração da janela
        setTitle("Lista de Tarefas");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modelo da lista de tarefas
        taskListModel = new DefaultListModel<>();

        // Lista de tarefas
        taskList = new JList<>(taskListModel);

        // Campo para adicionar novas tarefas
        newTaskField = new JTextField();
        JButton addButton = new JButton("Adicionar Tarefa");
        JButton removeButton = new JButton("Remover Tarefa");

        // Layout
        setLayout(new BorderLayout());
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(newTaskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        inputPanel.add(removeButton, BorderLayout.WEST);

        add(inputPanel, BorderLayout.SOUTH);

        // Adiciona evento ao botão para adicionar tarefas
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        // Adiciona evento ao botão para remover tarefas
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTask();
            }
        });

        // Adiciona evento de seleção à lista de tarefas
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setCellRenderer(new TaskListCellRenderer());

        // Adiciona evento de duplo clique na lista para marcar a tarefa como concluída
        taskList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int selectedIndex = taskList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        Task selectedTask = taskListModel.getElementAt(selectedIndex);
                        selectedTask.setCompleted(!selectedTask.isCompleted());
                        taskList.repaint(); // Atualiza a renderização da lista
                    }
                }
            }
        });
    }

    public void addTask() {
        String newTaskDescription = newTaskField.getText().trim();
        if (!newTaskDescription.isEmpty()) {
            Task newTask = new Task(newTaskDescription);
            taskListModel.addElement(newTask);
            newTaskField.setText("");
        }
    }

    public void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskListModel.remove(selectedIndex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskListApp().setVisible(true);
            }
        });
    }

    // Classe interna para representar uma tarefa
    public static class Task {
        public String description;
        public boolean completed;

        public Task(String description) {
            this.description = description;
            this.completed = false;
        }

        public String getDescription() {
            return description;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    // Classe interna para renderizar as células da lista de tarefas
    public static class TaskListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            Task task = (Task) value;
            setText(task.getDescription());

            // Altera a aparência com base no estado de conclusão da tarefa
            if (task.isCompleted()) {
                setForeground(Color.GRAY);
                setFont(getFont().deriveFont(Font.ITALIC));
            } else {
                setForeground(list.getForeground());
                setFont(getFont().deriveFont(Font.PLAIN));
            }

            return this;
        }
    }

    
}
