package controller;

import view.JFrame;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * @author Tyler
 **/
public class controller {
    public JFrame view;
    
    String path;
    String directory = "C:\\";
    String name;
    player player = new player();
    
    
    public controller(view.JFrame view) {
        this.view = view;
        MyButtonListener listen = new MyButtonListener ();
        this.view.addMyListener(listen);
    }
        

    class MyButtonListener implements ActionListener {
    @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(view.getBtnBrowse())){
                directory();
            } else if(e.getSource().equals(view.getBtnPlay())) {
                if(path != null){
                    player.Play(path);
                }
            }  else if(e.getSource().equals(view.getBtnPause())) {
                player.Pause();
            } else if(e.getSource().equals(view.getBtnStop())) {
                player.Stop();
            } 
        }
    }
    
    
    public void directory(){
        JFileChooser chooser = new JFileChooser(directory);
        chooser.setDialogTitle("Choose MP3 File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 Files", "mp3", "mpeg3");
        chooser.addChoosableFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);//removes all files option in file type selector
        if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            player.Stop();
            path = chooser.getSelectedFile().getPath();
            name = chooser.getSelectedFile().getName();
            directory = chooser.getCurrentDirectory().toString();
            this.view.setTxtDirectory(directory);
            this.view.setTxtSong(name);
        }
    }
}