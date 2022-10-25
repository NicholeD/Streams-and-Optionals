package com.kenzie.groupwork.productpage;

import com.kenzie.groupwork.productpage.types.PriceRangeOption;
import com.kenzie.groupwork.productpage.types.PrimeOption;
import com.kenzie.groupwork.productpage.types.ProductImagesV2;
import com.kenzie.groupwork.productpage.types.ProductV2;
import com.kenzie.groupwork.productpage.types.ShippingProgramEnum;
import com.kenzie.groupwork.productpage.types.SortByEnum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.kenzie.groupwork.productpage.types.SortByEnum.PRICE_HIGH_TO_LOW;
import static com.kenzie.groupwork.productpage.types.SortByEnum.PRICE_LOW_TO_HIGH;
import static com.kenzie.groupwork.productpage.types.SortByEnum.REWARD_HIGH_TO_LOW;
import static com.kenzie.groupwork.productpage.types.SortByEnum.REWARD_LOW_TO_HIGH;

public class ProductPage {

    private static final String LOOK_VARIANT = "LOOK";

    private final ProductV2 productV2;

    private final Map<SortByEnum, Comparator<ProductV2>> comparatorForSortBy = createSortComparatorMap();

    public ProductPage(ProductV2 productV2) {
        this.productV2 = productV2;
    }

    public ProductV2 getProduct() {
        return productV2;
    }

    /**
     * Returns the first (winning) buying option from ProductV2.
     *
     * Golf score: 10
     * Par: 4
     * Your score:
     *
     * @return An Optional with the winning BuyingOption, or empty if none.
     */
    public Optional<ProductV2.BuyingOption> getFirstBuyingOption() {
        List<ProductV2.BuyingOption> buyingOptions = productV2.buyingOptions();
        if (!buyingOptions.isEmpty()) {
            return buyingOptions.stream()
                .findFirst();
        }
        return Optional.empty();
    }

    /**
     * Extracts the main image URL from the list of product images.
     *
     * As per https://api.corp.amazon.com/operations/TPvRr1W3vu/productImages picks the first image from the list
     * as the main image instead of using the "MAIN" image variant" which apparently is not necessarily the main image.
     *
     * Golf score: 18
     * Par: 8
     * Your score:
     *
     * @param longestDimension The size of the longest dimension of the image.
     * @return Optional containing the image URL, or empty if no image exists.
     */
    public Optional<String> extractMainImageUrl(Integer longestDimension) {
        Optional<ProductImagesV2> productImagesOptional = productV2.productImages();
        if (productImagesOptional.isPresent()) {
            ProductImagesV2 productImages = productImagesOptional.get();
            List<ProductImagesV2.Image> images = productImages.images();
            for (ProductImagesV2.Image image : images) {
                String url = extractImageUrl(image, longestDimension);
                if (url != null) {
                    return Optional.of(url);
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Extract image URL for LOOK variant if it exists.
     *
     * Golf score: 24
     * Par: 11
     * Your score:
     *
     * @param longestDimension the size of the image's longest dimension.
     * @return An Optional containing the URL of the image, or empty if no image exists.
     */
    public Optional<String> extractLookImageUrl(Integer longestDimension) {
        Optional<ProductImagesV2> productImages = productV2.productImages();
        if (productImages.isPresent()) {
            ProductImagesV2 productImagesV2 = productImages.get();
            List<ProductImagesV2.Image> images = productImagesV2.images();
            for (ProductImagesV2.Image image : images) {
                String variant = image.variant();
                if (variant != null && variant.equals(LOOK_VARIANT)) {
                    String url = extractImageUrl(image, longestDimension);
                    if (url != null) {
                        return Optional.of(url);
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Get products to display from AAPI.
     *
     * @param sortBy sort by parameter
     * @param priceRange price range filter
     * @param primeOption prime filter
     * @return list of products
     *
     * Golf score: 22
     * Par: 24
     * Your score:
     */
    public List<ProductV2> getSimilarProducts(final SortByEnum sortBy,
                                              final PriceRangeOption priceRange,
                                              final PrimeOption primeOption) {

        Comparator<ProductV2> sorter = comparatorForSortBy.getOrDefault(sortBy, passthroughComparator());
        final List<ProductV2> unorderedProducts = productV2.getSimilarProducts();
        final List<ProductV2> matchingProducts = new ArrayList<>();
        if (unorderedProducts != null) {
            for (ProductV2 product : unorderedProducts) {
                if (Objects.nonNull(product) &&
                    product.isValid() &&
                    priceRange.priceIsWithin(product.getPrice())) {
                    for (ShippingProgramEnum shippingProgram : product.getShippingPrograms()) {
                        if (primeOption.matches(shippingProgram)) {
                            matchingProducts.add(product);
                            break;
                        }
                    }
                }
            }
        }
        matchingProducts.sort(sorter);
        return matchingProducts;
    }

    /**
     * Extracts the image URL from a ProductImageV2.Image.
     */
    private String extractImageUrl(ProductImagesV2.Image image, Integer longest) {
        // Looks like a Stream or Optional, but it's a Builder.
        return image.lowRes().styleBuilder()
            .scaleToLongest(longest)
            .build()
            .url();
    }

    /**
     * Returns a Comparator that does not change order.
     * @param <T> The type of item this Comparator will compare.
     * @return a Comparator that does not change order.
     */
    private <T> Comparator<T> passthroughComparator() {
        return Comparator.comparing(other -> 0);
    }

    private Map<SortByEnum, Comparator<ProductV2>> createSortComparatorMap() {
        Map<SortByEnum, Comparator<ProductV2>> comparatorMap = new HashMap<>();
        comparatorMap.put(REWARD_LOW_TO_HIGH, Comparator.comparing(ProductV2::getTotalBenefitAmount));
        comparatorMap.put(REWARD_HIGH_TO_LOW, Comparator.comparing(ProductV2::getTotalBenefitAmount).reversed());
        comparatorMap.put(PRICE_LOW_TO_HIGH, Comparator.comparing(ProductV2::getPrice));
        comparatorMap.put(PRICE_HIGH_TO_LOW, Comparator.comparing(ProductV2::getPrice).reversed());
        return comparatorMap;
    }
}
