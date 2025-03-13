#import <Foundation/Foundation.h>

@interface KSCrash : NSObject

+ (instancetype _Nonnull)sharedInstance;
- (void)reportUserException:(NSString *_Nonnull)name
                     reason:(NSString *_Nullable)reason
          callStackReturnAddresses:(NSArray<NSNumber *> *_Nonnull)callStackReturnAddresses
                     causes:(NSArray<NSException *> *_Nonnull)causes;

@end

@interface KSCrashReport : NSObject

@property (copy, nullable, nonatomic) NSString *errorClass;
@property (copy, nullable, nonatomic) NSString *errorMessage;
@property (copy, nonnull, nonatomic) NSArray *stacktrace;
@property (nonatomic) NSUInteger type;

@end

typedef NS_ENUM(NSUInteger, KSCrashErrorType) {
    KSCrashErrorTypeCocoa,
    KSCrashErrorTypeC,
    KSCrashErrorTypeReactNativeJs
};

@interface KSCrashStackframe : NSObject

+ (NSArray<KSCrashStackframe *> *_Nonnull)stackframesWithCallStackReturnAddresses:(NSArray<NSNumber *> *_Nonnull)callStackReturnAddresses;

@end
