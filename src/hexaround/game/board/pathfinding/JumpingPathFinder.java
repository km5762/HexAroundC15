//package hexaround.game.board.pathfinding;
//
//import hexaround.game.board.IBoard;
//import hexaround.game.board.geometry.HexPoint;
//import hexaround.game.board.geometry.IPoint;
//import hexaround.game.board.geometry.UnitVectors;
//import hexaround.game.board.geometry.Vector;
//import hexaround.game.board.pathfinding.pathvalidator.IPathValidator;
//import hexaround.game.board.pathfinding.pointvalidator.IPointValidator;
//import hexaround.game.creature.ICreature;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//
//public class JumpingPathFinder implements IPathFinder {
//
//    @Override
//    public Optional<List<IPoint>> findPath(IBoard board, ICreature creature, IPoint fromPoint, IPointValidator pointValidator, IPathValidator pathValidator) {
//        int scalar = 1;
//
//        while (scalar < creature.getMaxDistance()) {
//            for (Vector unitVector : UnitVectors.VECTORS) {
//                int straightLineNeighborX = fromPoint.getX() + unitVector.dX() * scalar;
//                int straightLineNeighborY = fromPoint.getY() + unitVector.dY() * scalar;
//                IPoint straightLinePoint = new HexPoint(straightLineNeighborX, straightLineNeighborY);
//
//                List<IPoint> path = new ArrayList<>();
//                path.add(fromPoint);
//                path.add(straightLinePoint);
//
//                if (pathValidator.validate(path, creature)){
//                    return Optional.of(path);
//                }
//            }
//        }
//        return Optional.empty();
//    }
//}
