import mpi.*;

public class Main {
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);

        int current_rank = MPI.COMM_WORLD.Rank();   // UID of process
        int size = MPI.COMM_WORLD.Size();           // Count of processes
        int TAG = 0;                                // Category of message

        int[] message = new int[1];
        message[0] = current_rank;

        if ((current_rank % 2) == 0) {
            if ((current_rank + 1) != size) {
                int destination_rank = current_rank + 1;
                MPI.COMM_WORLD.Send(message, 0, 1, MPI.INT, destination_rank, TAG);
                System.out.println("Process " + current_rank + " sent message to process " + destination_rank);
            }
        }
        else {
            int source_rank = current_rank - 1;
            Status status = MPI.COMM_WORLD.Recv(message, 0, 1, MPI.INT, source_rank, TAG);
            System.out.println("Process " + current_rank + " received: " + message[0]);
            System.out.println("Process " + status.source + " sent to the process " + current_rank + " message with tag " + status.tag);
        }

        MPI.Finalize();
    }
}