package racingcar.domain.car;

import java.util.HashSet;
import java.util.List;
import racingcar.domain.power.PowerGenerator;

public class Cars {

    private static final int MIN_CAR_NAMES_COUNT = 2;

    private final List<Car> cars;

    private Cars(final List<Car> cars) {
        this.cars = cars;
    }

    public static Cars generateCars(final List<String> carNames) {
        validateCarNamesCount(carNames);
        validateDuplicatedCarNames(carNames);

        final List<Car> cars = carNames.stream()
                .map(Car::createCar)
                .toList();

        return new Cars(cars);
    }

    private static void validateCarNamesCount(final List<String> carNames) {
        if (carNames.size() < MIN_CAR_NAMES_COUNT) {
            throw new IllegalArgumentException("자동차 이름의 개수는 2이상이여야 합니다.");
        }
    }

    private static void validateDuplicatedCarNames(final List<String> carNames) {
        final int distinctSize = new HashSet<>(carNames).size();
        if (carNames.size() != distinctSize) {
            throw new IllegalArgumentException("중복된 자동차 이름을 허용하지 않습니다.");
        }
    }

    public void race(PowerGenerator powerGenerator) {
        cars.forEach(car -> car.move(powerGenerator.generatePower()));
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Car> findCarsAtDistance(CarDistance distance) {
        return cars.stream()
                .filter(car -> car.isAtDistance(distance))
                .toList();
    }

    public CarDistance getMaxDistance() {
        int maxDistance = cars.stream()
                .mapToInt(Car::getDistance)
                .max()
                .orElseGet(() -> CarDistance.getInitialDistance().distance());
        return new CarDistance(maxDistance);
    }
}
