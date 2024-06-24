package lk.ijse.AutoCare_hub.repository;

import javafx.collections.ObservableList;
import lk.ijse.AutoCare_hub.db.DbConnection;
import lk.ijse.AutoCare_hub.model.Inventory_item;
import lk.ijse.AutoCare_hub.model.Point_System;
import lk.ijse.AutoCare_hub.model.Service;
import lk.ijse.AutoCare_hub.model.Tm.ItemTm;
import lk.ijse.AutoCare_hub.model.Vehicle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaceOrderRepo {
    public static boolean saveService(Service service, ObservableList<ItemTm> observableList) throws SQLException {
        Connection connection=null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean saveOrder = save(service);
            if (saveOrder) {
                boolean save = serviceDetailsSave(service.getServiceId(), observableList);
                if (save) {
                    boolean b = updateItemQty(observableList);
                    if (b) {
                        boolean pointSave = savePoint(service);
                        if (pointSave){
                            connection.commit();
                            System.out.println();
                            return true;
                        }

                    }
                }
            }
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }

    }

    private static boolean savePoint(Service service) throws SQLException {
        Vehicle vehicle = VehicleRepo.searchById(service.getVehicleId());
        if (vehicle!=null){
            Point_System pointDetails = Point_SystemRepo.search(vehicle.getCus_id());
            if (pointDetails!=null){
                //update point
                double cost = Double.parseDouble(service.getCost());
                double total = cost * 1 / 100;
                pointDetails.setTotal_point(String.valueOf(Integer.parseInt(pointDetails.getTotal_point())+total));
                Point_SystemRepo.update(pointDetails);
            }else {
                //save point
                String lastPointId =Point_SystemRepo.fintlastPoinId();
                String nextIs=null;
                if (lastPointId!=null){
                    int id = Integer.parseInt(lastPointId.substring(1));
                    nextIs="P00"+(id+1);
                }else {
                    nextIs="P00"+1;
                }
                double cost = Double.parseDouble(service.getCost());
                double total = cost * 1 / 100;
                Point_System point_system = new Point_System(nextIs,String.valueOf(total),vehicle.getCus_id(),service.getServiceId());
                boolean save = Point_SystemRepo.save(point_system);
            }
            return true;
        } else return false;
    }

    private static boolean updateItemQty(ObservableList<ItemTm> observableList) throws SQLException {
        for (ItemTm dto : observableList) {
            ResultSet resultSet = Inventory_itemRepo.searchItem(dto.getItemId());
            if (resultSet.next()){
                int x = Integer.parseInt(resultSet.getString(4));
                int subQty=x-Integer.parseInt(dto.getQty());
                boolean b = Inventory_itemRepo.update(new Inventory_item(dto.getItemId(), resultSet.getString(2), resultSet.getString(3), String.valueOf(subQty) ));
                if (!b) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean serviceDetailsSave(String serviceId, ObservableList<ItemTm> observableList) {
        for (ItemTm itemTm:observableList) {
            boolean b = ServiceRepo.saveServiceDetails(itemTm,serviceId);
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(Service service) {
        boolean b = ServiceRepo.saveService(service);
        return b;
    }
}
