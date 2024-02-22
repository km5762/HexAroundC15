//package hexaround.game.board.pathfinding;
//
//import hexaround.game.board.IBoard;
//import hexaround.game.board.geometry.IPoint;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//
//public class WalkingPathfinding implements PathfindingStrategy {
//    @Override
//    public boolean existsValidPath(IBoard board, IPoint fromPoint, IPoint toPoint) {
//        Queue<List<IPoint>> pathQueue = new LinkedList<>();
//        List<Integer> pathLengths = new ArrayList<>();
//
//        List<IPoint> startPath = new ArrayList<>();
//        startPath.add(fromPoint);
//        pathQueue.add(startPath);
//
//        while (!pathQueue.isEmpty()) {
//            List<IPoint> currentPath = pathQueue.poll();
//            IPoint lastPoint = currentPath.get(currentPath.size() - 1);
//
//            if (lastPoint.equals(toPoint)) {
//                pathLengths.add(currentPath.size() - 1);
//                System.out.println(currentPath);
//            }
//
//            for (IPoint neighboringPoint : lastPoint.getNeighboringPoints()) {
//                if (!currentPath.contains(neighboringPoint)
//                        && !board.getOccupiedNeighboringPoints(neighboringPoint).isEmpty()
//                        && getAllCreatures(neighboringPoint).isEmpty()) {
//                    List<IPoint> newPath = new ArrayList<>(currentPath);
//                    newPath.add(neighboringPoint);
//                    pathQueue.add(newPath);
//                }
//            }
//        }
//
//        return pathLengths;
//    }
//
//    @Override
//    public boolean canMove(IBoard board, IPoint fromPoint) {
//        return false;
//    }
//}
