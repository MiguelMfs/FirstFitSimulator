import java.util.*;


public class Main {

    private static final int RAM_SIZE = 50;      
    private static final int MAX_PROCESS_SIZE = 20;
    private static final int MAX_NEW_PER_CYCLE = 2;
    private static final int MAX_LIFETIME = 6;    
    private static final int SIMULATION_CYCLES = 20;

   
    private boolean[] ram;              
    private Random rnd;
    private List<Processo> processos;    
    private int pidCounter = 1;
    private int falhas = 0;

 
    private static class Processo {
        int pid;
        int start;  
        int size;    
        int lifetime;

        Processo(int pid, int start, int size, int lifetime) {
            this.pid = pid;
            this.start = start;
            this.size = size;
            this.lifetime = lifetime;
        }
    }


    public Main() {
        ram = new boolean[RAM_SIZE];
        Arrays.fill(ram, true);
        rnd = new Random();
        processos = new ArrayList<>();
    }


    public void runSimulation() {
        for (int ciclo = 1; ciclo <= SIMULATION_CYCLES; ciclo++) {
            System.out.println("=== Ciclo " + ciclo + " ===");

        
            int novos = rnd.nextInt(MAX_NEW_PER_CYCLE + 1); 
            for (int i = 0; i < novos; i++) {
                int size = 1 + rnd.nextInt(MAX_PROCESS_SIZE); 
                int life = 1 + rnd.nextInt(MAX_LIFETIME);   
                boolean ok = alocarProcesso(size, life);
                if (!ok) falhas++;
            }

          
            mostrarMemoria();

         
            List<Processo> finalizados = new ArrayList<>();
            for (Processo p : processos) {
                p.lifetime--;
                if (p.lifetime <= 0) finalizados.add(p);
            }
            for (Processo p : finalizados) {
                desalocar(p);
            }

       
            System.out.println("Processos ativos: " + processos.size()
                    + " | Falhas acumuladas: " + falhas
                    + " | Slots livres: " + contarLivres());
            System.out.println();
        }

        System.out.println("Simulação finalizada.");
    }

 
    private boolean alocarProcesso(int size, int life) {
        int start = encontrarFirstFit(size);
        if (start == -1) {
            System.out.println("Falha ao alocar processo " + pidCounter + " (size=" + size + ")");
            pidCounter++;
            return false;
        }

        for (int i = start; i < start + size; i++) {
            ram[i] = false; 
        }
        Processo p = new Processo(pidCounter, start, size, life);
        processos.add(p);
        System.out.println("Processo " + pidCounter + " alocado (start=" + start +
                ", size=" + size + ", life=" + life + ")");
        pidCounter++;
        return true;
    }

  
    private void desalocar(Processo p) {
        for (int i = p.start; i < p.start + p.size; i++) {
            ram[i] = true;
        }
        processos.remove(p);
        System.out.println("Processo " + p.pid + " finalizado. Memória liberada.");
    }

    
    private int encontrarFirstFit(int size) {
        int livresSeguidos = 0;
        for (int i = 0; i < RAM_SIZE; i++) {
            if (ram[i]) {
                livresSeguidos++;
                if (livresSeguidos == size) {
                    return i - size + 1;
                }
            } else {
                livresSeguidos = 0;
            }
        }
        return -1; 
    }


    private int contarLivres() {
        int c = 0;
        for (boolean b : ram) if (b) c++;
        return c;
    }

 
    private void mostrarMemoria() {
        StringBuilder sb = new StringBuilder();
        for (boolean b : ram) {
            sb.append(b ? 'L' : 'X');
        }
        System.out.println("Memória: " + sb.toString());
    }

    
    public static void main(String[] args) {
        Main sim = new Main();
        sim.runSimulation();
    }
}
