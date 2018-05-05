package controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class player {
    
    FileInputStream FIS;
    BufferedInputStream BIS;
    
    public Player player;
    public long pauseLoc;
    public long songLength;
    public boolean playable = false;//playable is to prevent it from playing multiple songs at the same time
    
    public void Stop(){
        if(player != null){
            player.close();
            pauseLoc = 0;
            songLength = 0; //stops resume from continuing paused song
            playable = true;
        } else {
            playable = true;
        }
    }
    
    public void Pause(){
        if(player != null){
            try {
                pauseLoc = FIS.available();
                player.close();
                playable = true;
            } catch (IOException ex) {
                
            }
        }
    }
    
    
    public void Play(String path){
        
        try {
            FIS = new FileInputStream(path);
            BIS = new BufferedInputStream(FIS);
            
            if(player != null){
                if(player.isComplete()){
                    Stop(); //if they hit play button after song is over
                }
            }
            
            if(pauseLoc != 0){
                FIS.skip(songLength - pauseLoc);
                player = new Player(BIS);
            } else if(player == null || playable == true){
                player = new Player(BIS);
                songLength = FIS.available();
            }
        } catch (JavaLayerException | IOException ex) {

        } 

        if(playable){
            playable = false;
            new Thread(){
                public void run(){
                    try {
                        player.play();
                    } catch (JavaLayerException ex) {

                    }
                }
            }.start();
        }
    }
}